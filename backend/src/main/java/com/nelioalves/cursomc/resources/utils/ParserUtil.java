package com.nelioalves.cursomc.resources.utils;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nelioalves.cursomc.domain.WeatherApi1;
import com.nelioalves.cursomc.domain.WeatherApi2;

public class ParserUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static WeatherApi1 parseWeatherApi1(String json) throws IOException {
        return objectMapper.readValue(json, WeatherApi1.class);
    }

    public static WeatherApi2 parseWeatherApi2(String json) throws IOException {
        return objectMapper.readValue(json, WeatherApi2.class);
    }
}