package com.rajan.foodDeliveryApp.config;

import com.rajan.foodDeliveryApp.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity() // default value is true prePostEnabled = true
@RequiredArgsConstructor
public class SecurityConfig {
    //    Tech Debt btw, I've enlisted most of the links because of time constraint
    private static final String[] AUTH_WHITELIST = {
            "/api/auth/login",
            "/api/auth/register",
            "/api/restaurants",
            "/api/restaurants/{id}",
            "/api/files/images/{filename}",
            "/api/restaurants/{restaurant_id}/menus",
            "/api/foods/menu/{id}",
            "/api/recommendations/restaurant",
            "/api/password-reset/*",
    };

    private final JwtAuthFilter jwtAuthFilter;

    /**
     * Considerations for Disabling CSRF
     * Disabling CSRF protection can make your application vulnerable to CSRF attacks.
     * However, in some cases, especially for stateless applications (e.g., RESTful APIs)
     * that use JWT for authentication, CSRF protection may not be necessary.
     * JWT tokens are generally sent as headers, which are not susceptible to CSRF attacks.
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(AUTH_WHITELIST).permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("https://bite-buddy-green.vercel.app/");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/api/**", config);
        return source;
    }
}
