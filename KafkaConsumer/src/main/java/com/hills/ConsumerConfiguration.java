package com.hills;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableKafka //for auto configuration from properties
public class ConsumerConfiguration {
	@Value("${kafka-group-one}")
	private String groupOne;
	
	@Value("${kafka-group-two}")
	private String groupTwo;

	@Value("${cluster}")
	private String cluster;
	
	@Bean
	public ConsumerFactory<String,Object> getConsumerForGroupOne(){
		Map<String,Object> map = new HashMap<>();
		map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, cluster);
		map.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		map.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		map.put(ConsumerConfig.GROUP_ID_CONFIG, groupOne);
		return new DefaultKafkaConsumerFactory<String,Object>(map);
	}
	
	@Bean(name="GroupOneConsumer")
	public ConcurrentKafkaListenerContainerFactory<String,Object> getGroupOneConsumerfactorty(){
		ConcurrentKafkaListenerContainerFactory<String,Object> groupOneConsumer = new ConcurrentKafkaListenerContainerFactory<String,Object>();
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
				System.out.println("Implement logic for recovery");
				ConsumerRecord<String,String> record = (ConsumerRecord<String, String>) context.getAttribute("record");
				System.out.println("record:"+record);
				handleRecovery(record);
			}else {
				System.out.println("Implement logic for non recovery");
				throw new RuntimeException(context.getLastThrowable().getMessage());
			}
			return null;
		});
		return groupOneConsumer;
	}
	
	private RetryTemplate retryTemplate() {
		FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
		backOffPolicy.setBackOffPeriod(1000);
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
	public void handleRecovery(ConsumerRecord<String,String> cRecord) {
		RestTemplate restTemplate =new RestTemplate();
		ResponseEntity<String> res = restTemplate.getForEntity("http://localhost:8080/sendMessage?message="+cRecord.value(), String.class);
		System.out.println("Recovered successfully: "+res.getBody());
	}

	@Bean
	public ConsumerFactory<String,Object> getConsumerForGrouptwo(){
		Map<String,Object> map = new HashMap<>();
		map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, cluster);
		map.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		map.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		map.put(ConsumerConfig.GROUP_ID_CONFIG, groupTwo);
		return new DefaultKafkaConsumerFactory<String,Object>(map);
	}
	
	@Bean(name="GroupTwoConsumer")
	public ConcurrentKafkaListenerContainerFactory<String,Object> getGroupTwoConsumerfactorty(){
		ConcurrentKafkaListenerContainerFactory<String,Object> groupTwoConsumer = new ConcurrentKafkaListenerContainerFactory<String,Object>();
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
				System.out.println("Implement logic for recovery");
				ConsumerRecord<String,String> record = (ConsumerRecord<String, String>) context.getAttribute("record");
				System.out.println("record:"+record);
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
