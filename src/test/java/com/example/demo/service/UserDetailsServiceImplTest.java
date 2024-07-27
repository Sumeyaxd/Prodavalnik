package com.example.demo.service;

import com.example.demo.model.entity.Role;
import com.example.demo.model.entity.User;
import com.example.demo.model.enums.RoleEnum;
import com.example.demo.model.user.UserDetailsDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserDetailsServiceImplTest {
    private UserDetailsServiceImpl userDetailsServiceToTest;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {
        this.userDetailsServiceToTest = new UserDetailsServiceImpl(mockUserRepository);
    }

    @Test
    void testLoadUserByUsername_UserFound() {

        Role user = new Role();
        user.setRole(RoleEnum.USER);
        Role admin = new Role();
        admin.setRole(RoleEnum.ADMINISTRATOR);

        User testUser = new User();
        testUser.setUsername("sumeyaxd");
        testUser.setPassword("sumeyauzunov");
        testUser.setFullName("Sumeya Uzunova");
        testUser.setRoles(List.of(admin, user));

        when(mockUserRepository.findByUsername("sumeyaxd"))
                .thenReturn(Optional.of(testUser));

        UserDetails userDetails = userDetailsServiceToTest.loadUserByUsername("sumeyaxd");

        assertInstanceOf(UserDetailsDTO.class, userDetails);

        UserDetailsDTO userDetailsDTO = (UserDetailsDTO) userDetails;

        assertEquals(testUser.getUsername(), userDetailsDTO.getUsername());
        assertEquals(testUser.getPassword(), userDetailsDTO.getPassword());

        List<String> expectedRoles = testUser.getRoles().stream().map(Role::getRole).map(r -> "ROLE_" + r).toList();
        List<String> actualRoles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        assertEquals(expectedRoles, actualRoles);
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        assertThrows(UsernameNotFoundException.class,
                () -> userDetailsServiceToTest.loadUserByUsername("sumeyaxdd"));
    }
}
