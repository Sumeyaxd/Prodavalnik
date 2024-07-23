package com.example.demo.service;

import com.example.demo.model.dto.AddSupplierDTO;
import com.example.demo.model.dto.SupplierDTO;
import com.example.demo.model.entity.Supplier;

import java.util.List;

public interface SupplierService {
    List<SupplierDTO> getAllSuppliers();

    SupplierDTO addSupplier(AddSupplierDTO addPSupplierDTO);

    void deleteSupplier(Long id);
}
