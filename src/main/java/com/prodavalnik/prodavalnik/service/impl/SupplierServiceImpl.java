package com.prodavalnik.prodavalnik.service.impl;

import com.prodavalnik.prodavalnik.model.dto.AddSupplierDTO;
import com.prodavalnik.prodavalnik.model.dto.SupplierDTO;
import com.prodavalnik.prodavalnik.service.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SupplierServiceImpl.class);
    private final RestClient restClient;

    @Value("http://localhost:8080")
    private String apiBaseUrl;

    public SupplierServiceImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public void addSupplier(AddSupplierDTO addSupplierDTO) {
        LOGGER.info("Adding new supplier...");

        this.restClient
                .post()
                .uri(apiBaseUrl + "/suppliers")
                .body(addSupplierDTO)
                .retrieve();
    }

    @Override
    public void deleteSupplier(Long id) {
        LOGGER.info("Delete supplier...");

        this.restClient
                .delete()
                .uri(apiBaseUrl + "/suppliers/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public List<SupplierDTO> getAllSuppliers() {
        LOGGER.info("Get all suppliers...");

        return this.restClient
                .get()
                .uri(apiBaseUrl + "/suppliers")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<SupplierDTO>>() {});
    }
}
