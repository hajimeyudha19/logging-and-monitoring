package com.ensat.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;
    private final Counter failedRequestCounter;

    @Value("${openweather.api.key}")
    private String apiKey;

    public WeatherService(RestTemplate restTemplate, MeterRegistry meterRegistry) {
        this.restTemplate = restTemplate;
        this.failedRequestCounter = meterRegistry.counter("custom.failed.requests");
    }

    @CircuitBreaker(name = "weatherService", fallbackMethod = "weatherFallback")
    public String getWeatherForJakarta() {
        String url = UriComponentsBuilder.fromHttpUrl("https://api.openweathermap.org/data/2.5/weather")
                .queryParam("q", "Jakarta")
                .queryParam("appid", apiKey)
                .toUriString();

        try {
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            failedRequestCounter.increment();
            throw e;
        }
    }

    public String weatherFallback(Throwable t) {
        return "Weather service is currently unavailable. Please try again later.";
    }
}
