package com.prodavalnik.prodavalnik.repository;

import com.prodavalnik.prodavalnik.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User map);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}