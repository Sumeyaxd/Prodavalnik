package com.example.demo.service;

import com.example.demo.model.Supplier;

import java.util.List;

public interface SupplierService {
    Supplier addSupplier(Supplier supplier);
    List<Supplier> getAllSuppliers();
    Supplier getSupplierById(Long id);
    void deleteSupplier(Long id);
}
