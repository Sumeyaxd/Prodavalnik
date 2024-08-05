package com.prodavalnik.prodavalnik.service;

import com.prodavalnik.prodavalnik.model.entity.Role;
import com.prodavalnik.prodavalnik.model.enums.RoleEnum;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByRole(RoleEnum role);
}
