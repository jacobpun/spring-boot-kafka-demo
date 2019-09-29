package com.pk.kakademo.admin;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.web.context.support.GenericWebApplicationContext;

@Configuration
/**
 * Dynamically creates kafka topics based on configuration
 */
public class KafkaTopicAdmin {
    @Autowired
    private KafkaAdmin admin;

    @Autowired
    private GenericWebApplicationContext context;

    @Autowired
    private TopicConfigurations configs;

    @PostConstruct
    public void postConstruct() {
        this.configs.getTopics().forEach(this::createTopic);
    }

    private void createTopic(TopicConfiguration config) {
        this.context.registerBean(config.getName(), NewTopic.class, config::toTopic);
    }
}