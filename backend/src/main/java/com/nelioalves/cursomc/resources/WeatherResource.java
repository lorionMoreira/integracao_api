package com.nelioalves.cursomc.resources;


import com.nelioalves.cursomc.domain.City;
import com.nelioalves.cursomc.domain.Result;
import com.nelioalves.cursomc.domain.WeatherApi1;
import com.nelioalves.cursomc.domain.WeatherApi2;
import com.nelioalves.cursomc.dto.CityDTO;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
    @PostMapping("/v1/get")
    public ResponseEntity<Result> getWeather(@RequestBody CityDTO cityDTO) {

        try {
            double[] coordinates = weatherService.parseLatAndLog(cityDTO.getLatAndLog());
            double latitude = coordinates[0];
            double longitude = coordinates[1];


            String response1 = weatherService.getWeatherForLateLong1(latitude,longitude);
            String response2 = weatherService.getWeatherForLateLong2(latitude,longitude);
            

            
            String fieldFromResponse1 = JsonUtil.extractFieldFromJson1(response1);
            String fieldFromResponse2 = JsonUtil.extractFieldFromJson2(response2);

            String tempJson1 = ParserUtil.createTemperatureJson(fieldFromResponse1);
            String tempJson2 = ParserUtil.createTemperatureJson(fieldFromResponse2);

            WeatherApi1 weatherApi1 = ParserUtil.parseWeatherApi1(tempJson1);
            WeatherApi2 weatherApi2 = ParserUtil.parseWeatherApi2(tempJson2);

            Result result = weatherCalculationService.calculateAverageTemperature(weatherApi1, weatherApi2);
            
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/v1/cities")
    public ResponseEntity<List<CityDTO>> getCities() {
        List<City> cities = weatherService.getCities();
       List<CityDTO> unifiedcities = weatherService.unifyCoordinates(cities);
        return ResponseEntity.ok(unifiedcities);
    }


}
