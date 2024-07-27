package com.example.demo.service;

import com.example.demo.model.entity.Role;
import com.example.demo.model.enums.RoleEnum;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class RoleServiceImplTest {
    private RoleServiceImpl roleServiceToTest;

    @Mock
    private RoleRepository mockRoleRepository;

    @BeforeEach
    public void setUp() {
        this.roleServiceToTest = new RoleServiceImpl(mockRoleRepository);
    }

    @Test
    public void testFindByName_Successful() {
        Role role = new Role();
        role.setRole(RoleEnum.ADMINISTRATOR);

        when(mockRoleRepository.findByRole(RoleEnum.ADMINISTRATOR)).thenReturn(Optional.of(role));

        Optional<Role> foundRole = roleServiceToTest.findByRole(RoleEnum.ADMINISTRATOR);

        assertEquals(RoleEnum.ADMINISTRATOR, foundRole.get().getRole());
    }

    @Test
    public void testFindByNameNotFound() {
        Role role = new Role();

        when(mockRoleRepository.findByRole(role.getRole())).thenReturn(Optional.empty());

        Optional<Role> foundRole = roleServiceToTest.findByRole(role.getRole());

        assertFalse(foundRole.isPresent());
    }
}
