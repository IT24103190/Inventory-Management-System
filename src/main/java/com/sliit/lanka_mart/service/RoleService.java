package com.sliit.lanka_mart.service;

import com.sliit.lanka_mart.model.Role;
import com.sliit.lanka_mart.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Integer roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with ID: " + roleId));
    }

    public Role getRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found with name: " + roleName));
    }

    public Role createRole(Role role) {
        if (roleRepository.existsByRoleName(role.getRoleName())) {
            throw new RuntimeException("Role already exists: " + role.getRoleName());
        }
        return roleRepository.save(role);
    }

    public void deleteRole(Integer roleId) {
        roleRepository.deleteById(roleId);
    }
}