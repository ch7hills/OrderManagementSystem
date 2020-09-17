package com.hills.kafka.kafkaConfig;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.hills.kafka.interfaces.CompletableFutureReplyingKafkaOperations;
import com.hills.kafka.models.Car;
import com.hills.kafka.serviceImpl.CompletableFutureReplyingKafkaTemplate;

@Configuration
@EnableKafka
public class KafkaConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Value("${spring.kafka.consumer.group-id}")
	private String groupId;

	@Value("${kafka.topic.car.request}")
	private String requestTopic;

	@Value("${kafka.topic.car.reply}")
	private String replyTopic;

	@Value("${kafka.request-reply.timeout-ms}")
	private Long replyTimeout;

	@Bean
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

		return props;
	}

	@Bean
	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		// props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
		// config.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
		return props;
	}

	@Bean
	public CompletableFutureReplyingKafkaOperations<String, String, Car> replyKafkaTemplate() {
		CompletableFutureReplyingKafkaTemplate<String, String, Car> requestReplyKafkaTemplate = new CompletableFutureReplyingKafkaTemplate<>(
				requestProducerFactory(), replyListenerContainer());
		requestReplyKafkaTemplate.setDefaultTopic(requestTopic);
		//requestReplyKafkaTemplate.setDefaultReplyTimeout(replyTimeout);
		return requestReplyKafkaTemplate;
	}

	@Bean
	public ProducerFactory<String, String> requestProducerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	@Bean
	public ConsumerFactory<String, Car> replyConsumerFactory() {
		JsonDeserializer<Car> jsonDeserializer = new JsonDeserializer<>();
		jsonDeserializer.addTrustedPackages("com.hills.kafka.models");
		return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(), jsonDeserializer);
	}

	@Bean
	public KafkaMessageListenerContainer<String, Car> replyListenerContainer() {
		ContainerProperties containerProperties = new ContainerProperties(replyTopic);
		return new KafkaMessageListenerContainer<>(replyConsumerFactory(), containerProperties);
	}

	@Bean
	public KafkaAdmin admin() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		return new KafkaAdmin(configs);
	}

	@Bean
	public NewTopic replyTopic() {
		Map<String, String> configs = new HashMap<>();
		configs.put("retention.ms", replyTimeout.toString());
		return new NewTopic(replyTopic, 2, (short) 2).configs(configs);
	}

}
