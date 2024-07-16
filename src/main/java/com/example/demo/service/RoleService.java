package com.example.demo.service;

import com.example.demo.model.entity.Role;
import com.example.demo.model.enums.RoleEnum;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByRole(RoleEnum role);
}
