package com.pk.kakademo.admin;

import org.apache.kafka.clients.admin.NewTopic;
import lombok.Data;

@Data
public class TopicConfiguration {
    private String name;
    private int numPartitions;
    private short replicationFactor;

    public NewTopic toTopic() {
        return new NewTopic(this.name, this.numPartitions, this.replicationFactor);
    }
}