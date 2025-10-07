package com.cinemaabyss.events.kafka.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MovieEvent {
    @JsonProperty("movie_id")
    @NotNull
    private Integer movieId;
    @NotBlank
    private String title;
    @NotBlank
    private String action;
    @NotNull
    @JsonProperty("user_id")
    private Integer userId;
}
