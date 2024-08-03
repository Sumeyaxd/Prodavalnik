package com.prodavalnik.prodavalnik.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "suppliers.api")
public class SupplierApiConfig {

    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }

    public SupplierApiConfig setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

}