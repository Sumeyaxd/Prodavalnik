package com.prodavalnik.prodavalnik.service;

import com.prodavalnik.prodavalnik.model.dto.AddSupplierDTO;
import com.prodavalnik.prodavalnik.model.dto.SupplierDTO;

import java.util.List;

public interface SupplierService {
    List<SupplierDTO> getAllSuppliers();

    SupplierDTO addSupplier(AddSupplierDTO addPSupplierDTO);

    void deleteSupplier(Long id);
}
