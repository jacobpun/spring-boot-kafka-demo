package com.pk.kakademo.listener;

import com.pk.kakademo.model.MemberLocation;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @KafkaListener(topics = "pk-test1", containerFactory = "stringKafkaListenerContainerFactory")
    public void listenString(String message) {
        System.out.println("Received String in group foo: " + message);
    }

    // Two consumers for the same topic    
    @KafkaListener(topics = "pk-test2", containerFactory = "memberKafkaListenerContainerFactory")
    public void listenMemberLocation(MemberLocation message) {
        System.out.println("Received Location (1): " + message);
    }

    @KafkaListener(topics = "pk-test2", containerFactory = "memberKafkaListenerContainerFactory")
    public void listenMemberLocation2(MemberLocation message) {
        System.out.println("Received Location (2): " + message);
    }
}