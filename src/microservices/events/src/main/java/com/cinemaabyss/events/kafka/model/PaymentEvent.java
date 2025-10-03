package com.cinemaabyss.events.kafka.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class PaymentEvent {
    @JsonProperty("payment_id")
    @NotNull
    private Long paymentId;
    @JsonProperty("user_id")
    @NotBlank
    private Long userId;
    @NotNull
    private BigDecimal amount;
    @NotBlank
    private String status;
    @NotBlank
    private OffsetDateTime timestamp;
    @NotBlank
    private String methodType;

}