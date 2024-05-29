package com.nelioalves.cursomc.services;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nelioalves.cursomc.domain.City;
import com.nelioalves.cursomc.domain.enums.CityData;
import com.nelioalves.cursomc.dto.CityDTO;

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

    public String getWeatherForLateLong1(Double lat ,Double longi) {
        String url = String.format(
            "https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=%s",
            lat, longi, api1Key
        );
        return restTemplate.getForObject(url, String.class);
    }

    public String getWeatherForLateLong2(Double lat ,Double longi) {
        String url = String.format(
            "https://api.weatherapi.com/v1/current.json?q=%f,%f&key=%s",
            lat, longi, api2Key
        );
        return restTemplate.getForObject(url, String.class);
    }

    //

    public List<City> getCities() {
        return Arrays.stream(CityData.values())
        .map(data -> new City(data.getName(), data.getLatitude(), data.getLongitude()))
        .collect(Collectors.toList());
    }

    public List<CityDTO> unifyCoordinates(List<City> cities) {
        return cities.stream().map(city -> new CityDTO(
            city.getName(),
            city.getLatitude() + "," + city.getLongitude()
        )).collect(Collectors.toList());
    }

    public double[] parseLatAndLog(String latAndLog) {
        String[] parts = latAndLog.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid latitude and longitude format.");
        }
        double latitude = Double.parseDouble(parts[0]);
        double longitude = Double.parseDouble(parts[1]);
        return new double[]{latitude, longitude};
    }

    //
}
