package com.nelioalves.cursomc.services;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.cache.annotation.Cacheable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class WeatherService {
    private final WebClient webClient;
    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);
    
    public WeatherService(WebClient.Builder webClientBuilder,
                            @Value("${openweathermap.api.key}") String apiKey) {

        

        this.webClient = webClientBuilder
                .baseUrl("https://api.openweathermap.org")
                .defaultUriVariables(Collections.singletonMap("API_KEY", apiKey))
                .build();
    }

    @Cacheable(value = "weatherCache", key = "#lat + ',' + #lon")
    public Mono<String> fetchWeather(double lat, double lon) {
        logger.info("Fetching weather data for coordinates: {}, {}", lat, lon);

        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/data/2.5/weather")
                        .queryParam("lat", lat)
                        .queryParam("lon", lon)
                        .queryParam("appid", "{API_KEY}")
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }


}
