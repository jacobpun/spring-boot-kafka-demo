package com.pk.kakademo.sender;

import com.pk.kakademo.model.MemberLocation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka-send")
public class MessageController {
    @Autowired
    @Qualifier("string-kafka-template")
    private KafkaTemplate<String, String> stringTemplate;

    @Autowired
    @Qualifier("member-kafka-template")
    private KafkaTemplate<String, MemberLocation> memberTemplate;

    // curl -H "content-type: text/plain" -d "message.. " -X POST http://localhost:8080/kafka-send/string
    @PostMapping(path = "string", consumes = MediaType.TEXT_PLAIN_VALUE)
    public void send(@RequestBody String message, @RequestParam String key) {
        this.stringTemplate.sendDefault(key, message)
            .addCallback(
                success -> System.out.println(
                    "Sent to partition: " + success.getRecordMetadata().partition() + 
                    ". Offset: "+ success.getRecordMetadata().offset()
                ),                 
                System.out::println
            );
    }

    // curl -H "content-type: application/json" -d '{"name": "pk", "id": "1", "latitude": 10, "longitude": 5}' -X POST http://localhost:8080/kafka-send/member
    @PostMapping(path = "member", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void send(@RequestBody MemberLocation location) {
        this.memberTemplate.sendDefault(location.getId(), location)
            .addCallback(
                success -> System.out.println(
                    "Sent to partition: " + success.getRecordMetadata().partition() + 
                    ". Offset: "+ success.getRecordMetadata().offset()
                ),                 
                System.out::println
            );
    }

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public String ping() {
        return "pong\r\n";
    }
}
