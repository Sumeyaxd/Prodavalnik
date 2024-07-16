package com.example.demo.service.impl;

import com.example.demo.model.ProdavalnikUserDetails;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.Role;
import com.example.demo.model.enums.RoleEnum;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ProdavalnikUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public ProdavalnikUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        return userRepository
                .findByEmail(email)
                .map(ProdavalnikUserDetailsService::map)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User with email " + email + " not found!"));
    }

    private static UserDetails map(User userEntity) {

        return new ProdavalnikUserDetails(
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getRoles().stream().map(Role::getRole).map(ProdavalnikUserDetailsService::map).toList(),
                userEntity.getFirstName(),
                userEntity.getLastName()
        );
    }

    private static GrantedAuthority map(RoleEnum role) {
        return new SimpleGrantedAuthority(
                "ROLE_" + role
        );
    }
}
