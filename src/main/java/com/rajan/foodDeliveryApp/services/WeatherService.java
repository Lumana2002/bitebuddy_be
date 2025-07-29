package com.rajan.foodDeliveryApp.services;

import com.rajan.foodDeliveryApp.domain.entities.WeatherData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${openweather.api.key}")
    private String apiKey;

    public WeatherData getWeather(double latitude, double longitude) {
        String url = "https://api.openweathermap.org/data/2.5/weather"
                + "?lat=" + latitude
                + "&lon=" + longitude
                + "&appid=" + apiKey
                + "&units=metric";

        RestTemplate restTemplate = new RestTemplate();
        WeatherData weatherData = restTemplate.getForObject(url, WeatherData.class);

        return weatherData;
    }
}
