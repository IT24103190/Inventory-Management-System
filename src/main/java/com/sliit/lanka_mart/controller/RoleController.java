package com.sliit.lanka_mart.controller;

import com.sliit.lanka_mart.model.Role;
import com.sliit.lanka_mart.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<Role> getRoleById(@PathVariable Integer roleId) {
        return ResponseEntity.ok(roleService.getRoleById(roleId));
    }

    @GetMapping("/name/{roleName}")
    public ResponseEntity<Role> getRoleByName(@PathVariable String roleName) {
        return ResponseEntity.ok(roleService.getRoleByName(roleName));
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roleService.createRole(role));
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable Integer roleId) {
        roleService.deleteRole(roleId);
        return ResponseEntity.noContent().build();
    }
}