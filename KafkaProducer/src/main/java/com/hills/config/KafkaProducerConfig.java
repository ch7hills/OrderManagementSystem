
package com.hills.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
//https://github.com/dilipsundarraj1/kafka-for-developers-using-spring-boot/
//import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.hills.model.Room;


@Configuration
@EnableKafka //for auto configuration from properties
public class KafkaProducerConfig {

	/*
	 * @Value("${kafka-group}") private String group;
	 */

	@Value("${spring.cluster}")
	private String cluster;
	
	@Value("${spring.kafka.ssl.trust-store-location}")
	private String trustStoreLocation;
	
	@Value("${spring.kafka.ssl.key-store-location}")
	private String keyStoreLocation;
	
	@Value("${spring.kafka.ssl.key-store-password}")
	private String keyStorePassword;
	
	@Value("${spring.kafka.ssl.trust-store-password}")
	private String trustStorePassword;

	@Bean
	public DefaultKafkaProducerFactory<String, Room> getProducer() {
		 /* Properties props = new Properties();
		  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapBrokers);
		  props.put(ProducerConfig.ACKS_CONFIG, "1");
		  props.put(ProducerConfig.RETRIES_CONFIG, 3);
		  props.put(ProducerConfig.BATCH_SIZE_CONFIG, 1638400);
		  props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
		  props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "gzip");
		  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
		  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
		  */
		
		Map<String, Object> config = new HashMap<>();
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, cluster);
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		config.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
		// //onfig.put(ProducerConfig.k r
		//config.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleProducer");

		config.put(ProducerConfig.ACKS_CONFIG, "all");
		config.put(ProducerConfig.RETRIES_CONFIG, "10");
		// Linger up to 100 ms before sending batch if size not met
		config.put(ProducerConfig.LINGER_MS_CONFIG, 100);

		// Batch up to 64K buffer sizes.
		//config.put(ProducerConfig.BATCH_SIZE_CONFIG, 16_384 * 4);

		// Use Snappy compression for batch compression.
		//config.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");

		// Request timeout - request.timeout.ms
		config.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 15_000);

		// Only retry after one second.
		config.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1_000);
		
		config.put("security.protocol", "SSL");
		config.put("ssl.truststore.location", trustStoreLocation);
		config.put("ssl.truststore.password", trustStorePassword);

		config.put("ssl.key.password", keyStorePassword);
		config.put("ssl.keystore.password", keyStorePassword);
		config.put("ssl.keystore.location", keyStoreLocation);
		return new DefaultKafkaProducerFactory<>(config);

	}

	@Bean
	public KafkaTemplate<String, Room> getKafkatemaplate() {
		return new KafkaTemplate<>(getProducer());
	}
}
