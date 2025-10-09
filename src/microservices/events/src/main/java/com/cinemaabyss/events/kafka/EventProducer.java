package com.cinemaabyss.events.kafka;


import com.cinemaabyss.events.kafka.model.MovieEvent;
import com.cinemaabyss.events.kafka.model.PaymentEvent;
import com.cinemaabyss.events.kafka.model.UserEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class EventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public EventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${app.topics.user}")
    private String userTopic;

    @Value("${app.topics.payment}")
    private String paymentTopic;

    @Value("${app.topics.movie}")
    private String movieTopic;

    public void sendToUserTopic(UserEvent payload) {
        send(userTopic, payload);
    }

    public void sendToPaymentTopic(PaymentEvent payload) {
        send(paymentTopic, payload);
    }

    public void sendToMovieTopic(MovieEvent payload) {
        send(movieTopic, payload);
    }

    private <T> void send(String topic, T payload) {
        String key = UUID.randomUUID().toString();
        var msg = MessageBuilder.withPayload(payload)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(KafkaHeaders.KEY, key)
                .setHeader("correlationId", key)
                .build();

        log.info("send: %s".formatted(payload));
        kafkaTemplate.send(msg);
    }
}
