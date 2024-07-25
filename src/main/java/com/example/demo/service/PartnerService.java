package com.example.demo.service;

import com.example.demo.model.dto.AddPartnerDTO;
import com.example.demo.model.dto.PartnerDTO;

import java.util.List;

public interface PartnerService {
    void addPartner(AddPartnerDTO addPartnerDTO);

    void deletePartner(long id);

    List<PartnerDTO> getAllPartners();
}