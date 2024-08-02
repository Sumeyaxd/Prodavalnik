package com.prodavalnik.prodavalnik.service;

import java.math.BigDecimal;


public interface EmailService {
    void sendRegistrationEmail(String userEmail, String username);

    void sendMakeOrderEmail(String email, String fullName, Long orderId, BigDecimal totalPrice,
                            String deliveryAddress, String phoneNumber);
}
