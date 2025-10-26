package com.sliit.lanka_mart.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Suppliers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SupplierID")
    private Integer supplierId;

    @Column(name = "SupplierName", nullable = false, length = 100)
    private String supplierName;

    @Column(name = "Email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "PhoneNumber", length = 20)
    private String phoneNumber;

    @Column(name = "CompanyName", nullable = false, length = 255)
    private String companyName;

    @JsonIgnore
    @Column(name = "PasswordHash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "IsActive", nullable = false)
    private Boolean isActive = true;

    @Column(name = "CreatedDate", nullable = false)
    private java.time.LocalDateTime createdDate = java.time.LocalDateTime.now();

    @Transient
    public String getFullName() {
        return supplierName;
    }
}




