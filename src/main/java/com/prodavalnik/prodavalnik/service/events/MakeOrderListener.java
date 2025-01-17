package com.prodavalnik.prodavalnik.service.events;

import com.prodavalnik.prodavalnik.service.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MakeOrderListener {
    private final EmailService emailService;

    public MakeOrderListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    public void handleMakeOrderEvent(MakeOrder makeOrderEvent) {

        this.emailService.sendMakeOrderEmail(makeOrderEvent.getEmail(), makeOrderEvent.getFullName(),
                makeOrderEvent.getOrderId(), makeOrderEvent.getTotalPrice(), makeOrderEvent.getDeliveryAddress(),
                makeOrderEvent.getPhoneNumber());
    }
}
