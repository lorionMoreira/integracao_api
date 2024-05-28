package com.nelioalves.cursomc.domain.enums;

public enum CityData {
    SALVADOR("Salvador", -12.98, -38.48),
    NEW_YORK("New York", 40.7128, -74.0060),
    TOKYO("Tokyo", 35.6895, 139.6917),
    SYDNEY("Sydney", -33.8688, 151.2093);

    private final String name;
    private final double latitude;
    private final double longitude;

    CityData(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
