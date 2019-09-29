package com.pk.kakademo.configuration;

import java.util.Map;

import com.pk.kakademo.model.MemberLocation;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;

@Configuration
public class SenderConfig {

    @Value("${kafka.topics[0].name}")
    private String stringTopic;

    @Value("${kafka.topics[1].name}")
    private String memberTopic;

    @Value("${spring.kafka.bootstrap-servers}")
    private String serverNameAndPort;

    @Bean
    public Map<String, Object> strtingProducerConfig() {
        return Map.of(
            BOOTSTRAP_SERVERS_CONFIG, serverNameAndPort,
            KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
            VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class
        );
    }

    @Bean
    public Map<String, Object> jsonProducerConfig() {
        return Map.of(
            BOOTSTRAP_SERVERS_CONFIG, serverNameAndPort,
            KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
            VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class
        );
    }

    @Bean
    public ProducerFactory<String, String> stringProducerFactory() {
        return new DefaultKafkaProducerFactory<>(strtingProducerConfig());
    }

    @Bean
    public ProducerFactory<String, MemberLocation> memberProducerFactory() {
        return new DefaultKafkaProducerFactory<>(jsonProducerConfig());
    }

    @Bean(name = "string-kafka-template")
    public KafkaTemplate<String, String> stringTemplate() {
        KafkaTemplate<String, String> template = new KafkaTemplate<>(stringProducerFactory());
        template.setDefaultTopic(stringTopic);
        return template;
    }   

    @Bean(name = "member-kafka-template")
    public KafkaTemplate<String, MemberLocation> memberTemplate() {
        KafkaTemplate<String, MemberLocation> template = new KafkaTemplate<>(memberProducerFactory());
        template.setDefaultTopic(memberTopic);
        return template;
    }   
}