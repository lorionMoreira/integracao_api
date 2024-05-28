package com.nelioalves.cursomc.dto;

public class CityDTO {
    private String name;
    private String latAndLog;

    public CityDTO() { }
    
    public CityDTO(String name, String latAndLog) {
        this.name = name;
        this.latAndLog = latAndLog;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatAndLog() {
        return latAndLog;
    }

    public void setLatAndLog(String latAndLog) {
        this.latAndLog = latAndLog;
    }

        
}
