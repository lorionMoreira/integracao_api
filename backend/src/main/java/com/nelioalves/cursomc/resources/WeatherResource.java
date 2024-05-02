package com.nelioalves.cursomc.resources;


import com.nelioalves.cursomc.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/weather")
public class WeatherResource {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/get")
    public Mono<String> getWeather() {
        double lat = 321;
        double lon = 321;
        return weatherService.fetchWeather(lat, lon);
    }
}
