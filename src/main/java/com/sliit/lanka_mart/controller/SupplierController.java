package com.sliit.lanka_mart.controller;

import com.sliit.lanka_mart.model.Supplier;
import com.sliit.lanka_mart.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable Integer id) {
        return supplierService.getSupplierById(id)
                .map(supplier -> ResponseEntity.ok(supplier))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier supplier) {
        try {
            Supplier newSupplier = supplierService.createSupplier(supplier);
            return ResponseEntity.status(HttpStatus.CREATED).body(newSupplier);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable Integer id, @RequestBody Supplier supplier) {
        if (supplierService.getSupplierById(id).isPresent()) {
            supplier.setSupplierId(id);
            Supplier updatedSupplier = supplierService.updateSupplier(supplier);
            return ResponseEntity.ok(updatedSupplier);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Integer id) {
        if (supplierService.getSupplierById(id).isPresent()) {
            supplierService.deleteSupplier(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}




