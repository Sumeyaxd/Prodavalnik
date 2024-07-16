package com.example.demo.repository;

import com.example.demo.model.entity.Role;
import com.example.demo.model.enums.RoleEnum;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(RoleEnum name);
}
