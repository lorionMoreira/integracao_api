package com.nelioalves.cursomc.services;

import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.Result;
import com.nelioalves.cursomc.domain.WeatherApi1;
import com.nelioalves.cursomc.domain.WeatherApi2;

@Service
public class WeatherCalculationService {

     public Result calculateAverageTemperature(WeatherApi1 weatherApi1, WeatherApi2 weatherApi2) {
        double average = (weatherApi1.getTemperature() + weatherApi2.getTemperature()) / 2;
        
        return new Result(average);
    }  
     
}
