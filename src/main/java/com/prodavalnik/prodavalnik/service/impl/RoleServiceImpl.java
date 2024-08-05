package com.prodavalnik.prodavalnik.service.impl;


import com.prodavalnik.prodavalnik.model.entity.Role;
import com.prodavalnik.prodavalnik.model.enums.RoleEnum;
import com.prodavalnik.prodavalnik.repository.RoleRepository;
import com.prodavalnik.prodavalnik.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public Optional<Role> findByRole(RoleEnum role) {
        return this.roleRepository.findByRole(role);
    }
}
