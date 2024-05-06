package com.nelioalves.cursomc.domain;

public class WeatherApi1 {
    private double temperature;

    public WeatherApi1() {}
    // Getters and Setters
    public double getTemperature() {
        return temperature;
    }
    public double getTemperature_c() {
        double temp_c = temperature - 273.15;
        return temp_c;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
