package com.example.demo.web;


import com.example.demo.model.dto.AddSupplierDTO;
import com.example.demo.model.dto.SupplierDTO;
import com.example.demo.service.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SupplierController.class);
    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
        return ResponseEntity.ok(
                this.supplierService.getAllPartners()
        );
    }

    @PostMapping
    public ResponseEntity<SupplierDTO> addSupplier(@RequestBody AddSupplierDTO addSupplierDTO) {

        LOGGER.info("Going to add a supplier {}", addSupplierDTO);

        SupplierDTO partnerDTO = this.supplierService.addPartner(addSupplierDTO);
        return ResponseEntity.
                created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(partnerDTO.getId())
                        .toUri()
                ).body(partnerDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SupplierDTO> deleteById(@PathVariable("id") Long id) {

        this.supplierService.deletePartner(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}