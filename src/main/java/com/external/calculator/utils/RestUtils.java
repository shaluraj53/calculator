package com.external.calculator.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestUtils {

    private final RestTemplate restTemplate;

    public RestUtils(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> T get(String url, Class<T> responseType) {
        try {
            return restTemplate.getForObject(url, responseType);
        } catch (HttpClientErrorException e) {
            return null;
        }
    }
}
