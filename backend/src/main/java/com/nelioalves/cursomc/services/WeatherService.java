package com.nelioalves.cursomc.services;
import org.springframework.web.reactive.function.client.WebClient;

public class WeatherService {
    private final WebClient webClient;

    public WeatherService(WebClient.Builder webClientBuilder,@Value("${openweathermap.api.key}") String apiKey) {

        this.webClient = webClientBuilder
                .baseUrl("https://api.openweathermap.org")
                .defaultUriVariables(Collections.singletonMap("API_KEY", apiKey))
                .build();
    }

    public Mono<String> fetchWeather(double lat, double lon) {
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
