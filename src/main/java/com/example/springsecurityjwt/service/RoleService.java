package com.example.springsecurityjwt.service;

import com.example.springsecurityjwt.entity.ERole;
import com.example.springsecurityjwt.entity.Roles;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface RoleService {
    Optional<Roles> findByRoleName(ERole roleName);
}
