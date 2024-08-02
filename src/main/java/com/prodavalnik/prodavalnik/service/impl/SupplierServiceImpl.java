package com.prodavalnik.prodavalnik.service.impl;

import com.prodavalnik.prodavalnik.model.dto.AddSupplierDTO;
import com.prodavalnik.prodavalnik.model.dto.SupplierDTO;
import com.prodavalnik.prodavalnik.model.entity.Supplier;
import com.prodavalnik.prodavalnik.repository.SupplierRepository;
import com.prodavalnik.prodavalnik.service.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;

    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<SupplierDTO> getAllSuppliers() {
        return this.supplierRepository.findAll().stream()
                .map(supplier -> this.modelMapper.map(supplier, SupplierDTO.class))
                .toList();
    }

    @Override
    public SupplierDTO addSupplier(AddSupplierDTO addSupplierDTO) {
        Supplier supplier = this.modelMapper.map(addSupplierDTO, Supplier.class);
        this.supplierRepository.saveAndFlush(supplier);

        return this.modelMapper.map(supplier, SupplierDTO.class);
    }

    @Override
    public void deleteSupplier(Long id) {
        this.supplierRepository.deleteById(id);
    }
}
