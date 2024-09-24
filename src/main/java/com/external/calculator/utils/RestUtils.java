package com.external.calculator.utils;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestUtils {

    private final RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(RestUtils.class);

    public RestUtils(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> T get(String url, Class<T> responseType) {
        try {
            return restTemplate.getForObject(url, responseType);
        } catch (HttpClientErrorException e) {
            logger.error("Error during REST Call", e);
            return null;
        }
    }
}
