package com.nelioalves.cursomc.resources;


import com.nelioalves.cursomc.domain.Result;
import com.nelioalves.cursomc.domain.WeatherApi1;
import com.nelioalves.cursomc.domain.WeatherApi2;
import com.nelioalves.cursomc.resources.utils.JsonUtil;
import com.nelioalves.cursomc.resources.utils.ParserUtil;
import com.nelioalves.cursomc.services.WeatherCalculationService;
import com.nelioalves.cursomc.services.WeatherService;

import java.io.IOException;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/weather")
public class WeatherResource {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private WeatherCalculationService weatherCalculationService;

     /* 
    @GetMapping("/get")
    public Mono<String> getWeather() {
        double lat = 44.34;
        double lon = 10.99;
        return weatherService.fetchWeather(lat, lon);
    }
    */
    @GetMapping("/v1/get")
    public ResponseEntity<Result> getWeather() {
        String city = "London";
        try {
            String response1 = weatherService.getWeatherForCity1(city);

            String response2 = weatherService.getWeatherForCity2(city);

            
            String fieldFromResponse1 = JsonUtil.extractFieldFromJson1(response1);
            String fieldFromResponse2 = JsonUtil.extractFieldFromJson2(response2);

            WeatherApi1 weatherApi1 = ParserUtil.parseWeatherApi1(response1);
            WeatherApi2 weatherApi2 = ParserUtil.parseWeatherApi2(response2);

            Result result = weatherCalculationService.calculateAverageTemperature(weatherApi1, weatherApi2);
            
            Map<String, String> responses = new HashMap<>();
            responses.put("temperatureFromAPI1", fieldFromResponse1);
            responses.put("temperatureFromAPI2", fieldFromResponse2);
        
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
