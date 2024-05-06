package com.nelioalves.cursomc.services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    private final RestTemplate restTemplate;

    @Value("${openweathermap.api.key}")
    private String api1Key;

    @Value("${weatherapicom.api.key}")
    private String api2Key;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getWeatherForCity1(String city) {
        String url = String.format(
            "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s",
            city, api1Key
        );
        return restTemplate.getForObject(url, String.class);
    }

    public String getWeatherForCity2(String city) {
        String url = String.format(
            "https://api.weatherapi.com/v1/current.json?q=%s&key=%s",
            city, api2Key
        );
        return restTemplate.getForObject(url, String.class);
    }
}
