package com.prodavalnik.prodavalnik.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "suppliers.api")
public class SuppliersApiConfig {

    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }

    public SuppliersApiConfig setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

}
