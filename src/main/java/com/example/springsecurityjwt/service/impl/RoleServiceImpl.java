package com.example.springsecurityjwt.service.impl;

import com.example.springsecurityjwt.entity.ERole;
import com.example.springsecurityjwt.entity.Roles;
import com.example.springsecurityjwt.repository.RoleRepository;
import com.example.springsecurityjwt.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Override
    public Optional<Roles> findByRoleName(ERole roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
