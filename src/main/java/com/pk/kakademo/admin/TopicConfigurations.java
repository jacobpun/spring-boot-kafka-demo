package com.pk.kakademo.admin;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "kafka")
@Getter
@Setter
public class TopicConfigurations {
    private List<TopicConfiguration> topics;
}