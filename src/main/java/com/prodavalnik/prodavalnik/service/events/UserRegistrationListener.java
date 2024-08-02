package com.prodavalnik.prodavalnik.service.events;

import com.prodavalnik.prodavalnik.service.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationListener {

    private final EmailService emailService;

    public UserRegistrationListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    public void handleUserRegistrationEvent(UserRegistration userRegistrationEvent) {

        this.emailService.sendRegistrationEmail(userRegistrationEvent.getUserEmail(),
                userRegistrationEvent.getUserFullName());
    }
}
