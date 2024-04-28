package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid role id: " + id));
    }


    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
