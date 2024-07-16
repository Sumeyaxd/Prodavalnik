package com.example.demo.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;


public interface EmailService {
    void sendRegistrationEmail(String userEmail, String username);

    void sendMakeOrderEmail(String email, String fullName, Long orderId, BigDecimal totalPrice,
                            String deliveryAddress, String phoneNumber);
}
