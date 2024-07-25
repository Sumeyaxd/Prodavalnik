package com.example.demo.service.impl;

import com.example.demo.model.dto.AddPartnerDTO;
import com.example.demo.model.dto.PartnerDTO;
import com.example.demo.service.PartnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
@Service
public class PartnerServiceImpl implements PartnerService {
    private Logger LOGGER = LoggerFactory.getLogger(PartnerServiceImpl.class);
    private final RestClient restClient;

    public PartnerServiceImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public void addPartner(AddPartnerDTO addPartnerDTO) {
        LOGGER.info("Adding new partner...");

        this.restClient
                .post()
                .uri("http://localhost:8080/partners")
                .body(addPartnerDTO)
                .retrieve();
    }

    @Override
    public void deletePartner(long id) {
        LOGGER.info("Delete partner...");

        this.restClient
                .delete()
                .uri("http://localhost:8080/partners/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public List<PartnerDTO> getAllPartners() {
        LOGGER.info("Get all partners...");

        return this.restClient
                .get()
                .uri("http://localhost:8080/partners")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }
}
