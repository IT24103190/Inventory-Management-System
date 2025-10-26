package com.sliit.lanka_mart.service;

import com.sliit.lanka_mart.model.Supplier;
import com.sliit.lanka_mart.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Optional<Supplier> getSupplierById(Integer id) {
        return supplierRepository.findById(id);
    }

    public Optional<Supplier> getSupplierByEmail(String email) {
        return supplierRepository.findByEmail(email);
    }

    public Supplier createSupplier(Supplier supplier) {
        supplier.setPasswordHash(passwordEncoder.encode(supplier.getPasswordHash()));
        return supplierRepository.save(supplier);
    }

    public Supplier updateSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public void deleteSupplier(Integer id) {
        supplierRepository.deleteById(id);
    }

    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public Supplier authenticateSupplier(String email, String password) {
        Optional<Supplier> supplierOpt = supplierRepository.findByEmailAndIsActiveTrue(email);
        if (supplierOpt.isPresent()) {
            Supplier supplier = supplierOpt.get();
            if (validatePassword(password, supplier.getPasswordHash())) {
                return supplier;
            }
        }
        return null;
    }
}




