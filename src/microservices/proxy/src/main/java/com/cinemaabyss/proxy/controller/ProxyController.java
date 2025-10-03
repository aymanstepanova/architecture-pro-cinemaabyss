package com.cinemaabyss.proxy.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ProxyController {

    private final WebClient monolithClient;
    private final WebClient moviesClient;

    private final boolean gradualMigration;
    private final int moviesMigrationPercent;

    public ProxyController(
            @Qualifier("monolithWebClient") WebClient monolithClient,
            @Qualifier("moviesWebClient") WebClient moviesClient,
            @Value("${integration.gradual.migration}") boolean gradualMigration,
            @Value("${integration.movies.migration.percent}") int moviesMigrationPercent
    ) {
        this.monolithClient = monolithClient;
        this.moviesClient = moviesClient;
        this.gradualMigration = gradualMigration;
        this.moviesMigrationPercent = Math.max(0, Math.min(100, moviesMigrationPercent));
    }

    // --- Movies ---
    @GetMapping(value = "/movies", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<String>> getMovies() {
        WebClient target = pickMoviesTarget();
        return target.get().uri("/api/movies")
                .retrieve()
                .toEntity(String.class);
    }

    @PostMapping(value = "/movies",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<String>> createMovie(@RequestBody String body) {
        WebClient target = pickMoviesTarget();
        return target.post().uri("/api/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .toEntity(String.class);
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<String>> getUsers(@RequestParam(value = "id", required = false) String id) {
        String uri = id == null ? "/api/users" : "/api/users?id=" + id;
        return monolithClient.get().uri(uri)
                .retrieve()
                .toEntity(String.class);
    }

    private WebClient pickMoviesTarget() {
        if (!gradualMigration) {
            return moviesClient;
        }
        int rand = (int) (Math.random() * 100);
        if (rand < moviesMigrationPercent) {
            return moviesClient;
        }
        return monolithClient;
    }
}



