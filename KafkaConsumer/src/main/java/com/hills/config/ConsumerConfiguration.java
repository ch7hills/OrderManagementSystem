package com.hills.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

import com.hills.models.Room;
import com.hills.models.TopicRecovery;
import com.hills.repositories.TopicRecoveryRepository;

@Configuration
@EnableKafka //for auto configuration from properties
public class ConsumerConfiguration {
	@Value("${spring.kafka.group-one}")
	private String groupOne;
	
	@Value("${spring.kafka.group-two}")
	private String groupTwo;

	@Value("${spring.cluster}")
	private String cluster;
	
	@Autowired
	TopicRecoveryRepository repo;
	
	@Value("${spring.kafka.ssl.trust-store-location}")
	private String trustStoreLocation;
	
	@Value("${spring.kafka.ssl.key-store-location}")
	private String keyStoreLocation;
	
	@Value("${spring.kafka.ssl.key-store-password}")
	private String keyStorePassword;
	
	@Value("${spring.kafka.ssl.trust-store-password}")
	private String trustStorePassword;
	
	@Bean
	public ConsumerFactory<String,Room> getConsumerForGroupOne(){
		Map<String,Object> map = new HashMap<>();
		map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, cluster);
		map.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		map.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		map.put(ConsumerConfig.GROUP_ID_CONFIG, groupOne);
		map.put("security.protocol", "SSL");
		map.put("ssl.truststore.location", trustStoreLocation);
		map.put("ssl.truststore.password", trustStorePassword);

		map.put("ssl.key.password", keyStorePassword);
		map.put("ssl.keystore.password", keyStorePassword);
		map.put("ssl.keystore.location", keyStoreLocation);
		return new DefaultKafkaConsumerFactory<>(map,new StringDeserializer(),new JsonDeserializer<>(Room.class));
	}
	
	@Bean(name="GroupOneConsumer")
	public ConcurrentKafkaListenerContainerFactory<String,Room> getGroupOneConsumerfactorty(){
		ConcurrentKafkaListenerContainerFactory<String,Room> groupOneConsumer = new ConcurrentKafkaListenerContainerFactory<String,Room>();
		groupOneConsumer.setConsumerFactory(getConsumerForGroupOne());
		//Setting concurrency
		groupOneConsumer.setConcurrency(2);
		//Exception handler
		groupOneConsumer.setErrorHandler((ThrownException,data)->{
			System.out.println("GroupOneConsumer Exception:"+ThrownException.getMessage()+" Data:"+data);
		});
		//setting retry template
		groupOneConsumer.setRetryTemplate(retryTemplate());
		groupOneConsumer.setRecoveryCallback(context ->{
			if(context.getLastThrowable().getCause() instanceof RecoverableDataAccessException) {
				//System.out.println("Implement logic for recovery grp1");
				ConsumerRecord<String,Room> record = (ConsumerRecord<String, Room>) context.getAttribute("record");
				//System.out.println("record:"+record);
				handleRecovery(record);
			}else {
				//System.out.println("Implement logic for non recovery");
				throw new RuntimeException(context.getLastThrowable().getMessage());
			}
			return null;
		});
		return groupOneConsumer;
	}
	
	private RetryTemplate retryTemplate() {
		FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
		backOffPolicy.setBackOffPeriod(1000);//1 second delay
		RetryTemplate retryTemplate = new RetryTemplate();
		retryTemplate.setBackOffPolicy(backOffPolicy);
		retryTemplate.setRetryPolicy(simpleRetryPolicy());
		return retryTemplate;
	}

	private RetryPolicy simpleRetryPolicy() {
		Map<Class<? extends Throwable>,Boolean> exceptions = new HashMap<>();
		exceptions.put(IllegalArgumentException.class, false);
		exceptions.put(RecoverableDataAccessException.class, true);
		SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy(3,exceptions);
		//simpleRetryPolicy.setMaxAttempts(3);
		return simpleRetryPolicy;
	}
	public void handleRecovery(ConsumerRecord<String,Room> cRecord) {
		Optional<TopicRecovery> recoveryOpt = repo.findByMessageAndType(cRecord.value().toString(),"consumer");
		int attempts = recoveryOpt.isPresent()?recoveryOpt.get().getAttempts():1;
		TopicRecovery recovery = recoveryOpt.isPresent()?recoveryOpt.get(): new TopicRecovery();
		recovery.setAttempts(attempts);
		recovery.setType("consumer");
		recovery.setMessage(cRecord.value().toString());
		recovery.setTopicName(cRecord.topic());
		repo.save(recovery);
		
		RestTemplate restTemplate =new RestTemplate();
		//ResponseEntity<String> res = restTemplate.posttForEntity("http://localhost:8080/sendMessage, String.class);
		ResponseEntity<String> res = restTemplate.postForEntity("http://localhost:8080/sendMessage", cRecord.value(), String.class);
		System.out.println("Recovered successfully: "+res.getBody()+" from partion:"+cRecord.partition()+ " for value:"+cRecord.value());
	}

	@Bean
	public ConsumerFactory<String,Room> getConsumerForGrouptwo(){
		Map<String,Object> map = new HashMap<>();
		map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, cluster);
		map.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		map.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		map.put(ConsumerConfig.GROUP_ID_CONFIG, groupTwo);
		return new DefaultKafkaConsumerFactory<>(map,new StringDeserializer(),new JsonDeserializer<>(Room.class));
	}
	
	@Bean(name="GroupTwoConsumer")
	public ConcurrentKafkaListenerContainerFactory<String,Room> getGroupTwoConsumerfactorty(){
		ConcurrentKafkaListenerContainerFactory<String,Room> groupTwoConsumer = new ConcurrentKafkaListenerContainerFactory<String,Room>();
		groupTwoConsumer.setConsumerFactory(getConsumerForGrouptwo());
		//Setting concurrency
		groupTwoConsumer.setConcurrency(2);
		//Exception handler
		groupTwoConsumer.setErrorHandler((ThrownException,data)->{
			System.out.println("GroupTwoConsumer Exception:"+ThrownException.getMessage()+" Data:"+data);
		});
		//setting retry template
		groupTwoConsumer.setRetryTemplate(retryTemplate());
		groupTwoConsumer.setRecoveryCallback(context ->{
			if(context.getLastThrowable().getCause() instanceof RecoverableDataAccessException) {
				//System.out.println("Implement logic for recovery grp2");
				ConsumerRecord<String,Room> record = (ConsumerRecord<String, Room>) context.getAttribute("record");
				//System.out.println("record:"+record);
				handleRecovery(record);
			}else {
				System.out.println("Implement logic for non recovery");
				throw new RuntimeException(context.getLastThrowable().getMessage());
			}
			return null;
		});
		return groupTwoConsumer;
	}
	
}
