package com.cinemaabyss.events.controller;

import com.cinemaabyss.events.kafka.EventProducer;
import com.cinemaabyss.events.kafka.model.MovieEvent;
import com.cinemaabyss.events.kafka.model.PaymentEvent;
import com.cinemaabyss.events.kafka.model.UserEvent;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventsController {
    private final EventProducer producer;

    @GetMapping(value = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> health() {
        Map<String, Object> resp = new HashMap<>();
        resp.put("status", true);
        return resp;
    }

    @PostMapping(value = "/movie", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> movie(@RequestBody @Valid MovieEvent body) {
        producer.sendToMovieTopic(body);

        return ResponseEntity.status(201).body(success(body));
    }

    @PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> user(@RequestBody UserEvent body) {
        producer.sendToUserTopic(body);
        return ResponseEntity.status(201).body(success(body));
    }

    @PostMapping(value = "/payment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> payment(@RequestBody PaymentEvent body) {
        producer.sendToPaymentTopic(body);
        return ResponseEntity.status(201).body(success(body));
    }

    private Map<String, Object> success(Object event) {
        Map<String, Object> resp = new HashMap<>();
        resp.put("status", "success");
        resp.put("partition", 0);
        resp.put("offset", 0);
        resp.put("event", event);
        return resp;
    }
}


