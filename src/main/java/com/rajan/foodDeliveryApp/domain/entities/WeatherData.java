package com.rajan.foodDeliveryApp.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherData {

    private Weather[] weather;
    private Main main;
    private Rain rain;

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weather {
        private String main;
        private String description;

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class Main {
        private Double temp;
        private Double humidity;

        public Double getTemp() {
            return temp;
        }

        public void setTemp(Double temp) {
            this.temp = temp;
        }

        public Double getHumidity() {
            return humidity;
        }

        public void setHumidity(Double humidity) {
            this.humidity = humidity;
        }
    }

    public static class Rain {
        @JsonProperty("1h")
        private Double oneHour;

        public Double getOneHour() {
            return oneHour;
        }

        public void setOneHour(Double oneHour) {
            this.oneHour = oneHour;
        }
    }
}
