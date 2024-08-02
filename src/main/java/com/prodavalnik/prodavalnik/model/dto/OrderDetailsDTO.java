package com.prodavalnik.prodavalnik.model.dto;

import com.prodavalnik.prodavalnik.model.enums.OrderStatusEnum;

import java.math.BigDecimal;

public class OrderDetailsDTO {
    private Long id;

    private String orderedOn;

    private String deliveredOn;

    private OrderStatusEnum status;

    private BigDecimal totalPrice;

    public OrderDetailsDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderedOn() {
        return orderedOn;
    }

    public void setOrderedOn(String orderedOn) {
        this.orderedOn = orderedOn;
    }

    public String getDeliveredOn() {
        return deliveredOn;
    }

    public void setDeliveredOn(String deliveredOn) {
        this.deliveredOn = deliveredOn;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
