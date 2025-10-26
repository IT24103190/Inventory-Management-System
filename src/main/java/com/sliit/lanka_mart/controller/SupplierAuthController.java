package com.sliit.lanka_mart.controller;

import com.sliit.lanka_mart.model.Supplier;
import com.sliit.lanka_mart.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/supplier-auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SupplierAuthController {

    private final SupplierService supplierService;

    @PostMapping("/login")
    public ResponseEntity<SupplierLoginResponse> login(@RequestBody SupplierLoginRequest request) {
        try {
            Supplier supplier = supplierService.authenticateSupplier(request.email(), request.password());

            if (supplier != null) {
                return ResponseEntity.ok(new SupplierLoginResponse(
                        true,
                        "Login successful",
                        supplier.getSupplierId(),
                        supplier.getEmail(),
                        supplier.getSupplierName(),
                        supplier.getCompanyName(),
                        null // Token would be generated here in production
                ));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new SupplierLoginResponse(false, "Invalid credentials", null, null, null, null, null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new SupplierLoginResponse(false, "Invalid credentials", null, null, null, null, null));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Supplier> register(@RequestBody Supplier supplier) {
        try {
            Supplier newSupplier = supplierService.createSupplier(supplier);
            return ResponseEntity.status(HttpStatus.CREATED).body(newSupplier);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    record SupplierLoginRequest(String email, String password) {}

    record SupplierLoginResponse(
            Boolean success,
            String message,
            Integer supplierId,
            String email,
            String supplierName,
            String companyName,
            String token
    ) {}
}




