package com.prodavalnik.prodavalnik.service;

import com.prodavalnik.prodavalnik.model.dto.AddSupplierDTO;
import com.prodavalnik.prodavalnik.model.dto.SupplierDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface SupplierService {
    List<SupplierDTO> getAllSuppliers();

    void addSupplier(AddSupplierDTO addPSupplierDTO);

    void deleteSupplier(Long id);
}
