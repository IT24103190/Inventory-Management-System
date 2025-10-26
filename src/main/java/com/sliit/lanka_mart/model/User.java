package com.sliit.lanka_mart.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "UserID", length = 20)
    private String userId;

    @Column(name = "FirstName", nullable = false, length = 100)
    private String firstName;

    @Column(name = "LastName", length = 100)
    private String lastName;

    @Column(name = "Email", nullable = false, unique = true, length = 255)
    private String email;

    @JsonIgnore
    @Column(name = "PasswordHash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "CompanyName", length = 255)
    private String companyName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RoleID", nullable = false)
    private Role role;

    @Transient
    public String getFullName() {
        return firstName + (lastName != null ? " " + lastName : "");
    }
}