package com.prodavalnik.prodavalnik.init;

import com.prodavalnik.prodavalnik.model.entity.Role;
import com.prodavalnik.prodavalnik.model.entity.User;
import com.prodavalnik.prodavalnik.model.enums.RoleEnum;
import com.prodavalnik.prodavalnik.repository.RoleRepository;
import com.prodavalnik.prodavalnik.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.prodavalnik.prodavalnik.model.enums.RoleEnum.USER;

@Component
public class AdminRolesInit implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminRolesInit(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (this.roleRepository.count() == 0) {

            Arrays.stream(RoleEnum.values())
                    .forEach(setRole -> {
                        Role role = new Role();
                        role.setRole(setRole);

                        String description = switch (setRole) {
                            case ADMINISTRATOR -> "Admin can register, login, add offers, remove offers, make orders, delete orders, add comments and delete comments.";
                            case USER -> "User can register, login, make orders, delete orders, add comments and delete comments.";
                        };

                        role.setDescription(description);
                        this.roleRepository.saveAndFlush(role);
                    });
        }

        if (this.userRepository.count() == 0) {

            User user = new User();
            Optional<Role> optionalAdmin = this.roleRepository.findByRole(RoleEnum.ADMINISTRATOR);
            Optional<Role> optionalUser = this.roleRepository.findByRole(RoleEnum.USER);

            List<Role> roles = new ArrayList<>();

            if (optionalAdmin.isPresent() && optionalUser.isPresent()) {
                roles.add(optionalAdmin.get());
                roles.add(optionalUser.get());
            }

            user.setUsername("admin");
            user.setFullName("Main Admin");
            user.setPhoneNumber("0812345678");
            user.setEmail("admin_support@gmail.com");
            user.setAddress("ul. Don 6, Plovdiv");
            user.setPassword(this.passwordEncoder.encode("Administrator05"));
            user.setComments(new ArrayList<>());
            user.setOrders(new ArrayList<>());
            user.setRoles(roles);

            this.userRepository.saveAndFlush(user);
        }
    }

}