package com.sliit.lanka_mart.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoleID")
    private Integer roleId;

    @Column(name = "RoleName", nullable = false, unique = true, length = 50)
    private String roleName;

    public enum RoleType {
        INVENTORY_MANAGER,
        WAREHOUSE_SUPERVISOR,
        SALES_EXECUTIVE,
        ACCOUNTANT,
        ADMIN
    }
}