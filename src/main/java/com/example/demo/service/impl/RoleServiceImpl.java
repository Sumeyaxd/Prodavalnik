package com.example.demo.service.impl;


import com.example.demo.model.entity.Role;
import com.example.demo.model.enums.RoleEnum;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.RoleService;
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
