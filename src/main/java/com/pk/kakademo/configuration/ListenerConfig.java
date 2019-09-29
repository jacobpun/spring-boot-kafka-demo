package com.pk.kakademo.configuration;

import java.util.Map;
import com.pk.kakademo.model.MemberLocation;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

@Configuration
@EnableKafka
public class ListenerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String serverNameAndPort;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> 
      stringKafkaListenerContainerFactory() {
        Map<String, Object> props = Map.of(
          BOOTSTRAP_SERVERS_CONFIG, serverNameAndPort,
          GROUP_ID_CONFIG, "foo",
          KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
          VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        ConsumerFactory<String, String> consumerFactory =  new DefaultKafkaConsumerFactory<>(props);
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MemberLocation> memberKafkaListenerContainerFactory() {
        Map<String, Object> props = Map.of(
                BOOTSTRAP_SERVERS_CONFIG, serverNameAndPort, 
                GROUP_ID_CONFIG, "foo",
                KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class, 
                VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        ConsumerFactory<String, MemberLocation> consumerFactory =  new DefaultKafkaConsumerFactory<>(
            props, 
            new StringDeserializer(), 
            new JsonDeserializer<>(MemberLocation.class)
        );
        ConcurrentKafkaListenerContainerFactory<String, MemberLocation> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}