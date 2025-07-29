package com.rajan.foodDeliveryApp.domain.dto;

public class WeatherDataDto {
    private String condition;
    private double temperature;
    private double humidity;
    private double rainVolume;

    public WeatherDataDto(String condition, double temperature, double humidity, double rainVolume) {
        this.condition = condition;
        this.temperature = temperature;
        this.humidity = humidity;
        this.rainVolume = rainVolume;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getRainVolume() {
        return rainVolume;
    }

    public void setRainVolume(double rainVolume) {
        this.rainVolume = rainVolume;
    }
}
