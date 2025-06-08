package com.example.servicestation.kafka.producer;

import com.example.servicestation.kafka.event.RequestStatusEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaRequestProducer {

    private final KafkaTemplate<String, RequestStatusEvent> kafkaTemplate;

    public void sendStatusChangeEvent(RequestStatusEvent event) {
        kafkaTemplate.send("request-status-events", event);
        log.info("Send status change event: {}", event);
    }
}
