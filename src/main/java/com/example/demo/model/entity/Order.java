package com.example.demo.model.entity;

import com.example.demo.model.enums.OrderStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{

    @Column(name = "delivery_address", nullable = false)
    @Size(min = 3, max = 100)
    private String deliveryAddress;

    @Column(name = "phone_number", nullable = false)
    @Size(min = 7, max = 15)
    private String phoneNumber;

    @Column(name = "total_price", nullable = false)
    @Positive
    private BigDecimal totalPrice;

    @Column(name = "ordered_on", nullable = false)
    private LocalDateTime orderedOn;

    @Column(name = "delivered_on")
    private LocalDateTime deliveredOn;

    @Column(name = "is_delivered", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private User client;

    @ManyToMany
    @JoinTable(name = "orders_products",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "offer_id", referencedColumnName = "id"))
    private List<Offer> offers;

    public Order() {
        this.offers = new ArrayList<>();
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getOrderedOn() {
        return orderedOn;
    }

    public void setOrderedOn(LocalDateTime orderedOn) {
        this.orderedOn = orderedOn;
    }

    public LocalDateTime getDeliveredOn() {
        return deliveredOn;
    }

    public void setDeliveredOn(LocalDateTime deliveredOn) {
        this.deliveredOn = deliveredOn;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public String parseDateToString(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return date.format(formatter);
    }

}
