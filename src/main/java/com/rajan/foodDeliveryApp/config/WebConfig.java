package com.rajan.foodDeliveryApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Maps /images/** → D:/Projects/images/
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:H:/Projects/images/");
//                .addResourceLocations("file:/home/rajan/Projects/lumana/images/");
    }
}