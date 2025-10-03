package com.cinemaabyss.events.kafka.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserEvent {
    @JsonProperty("user_id")
    private String userId;
    private String username;
    private String action;
    private String timestamp;

    public UserEvent() {}
    public UserEvent(String userId, String action) { this.userId = userId; this.action = action; }

}
