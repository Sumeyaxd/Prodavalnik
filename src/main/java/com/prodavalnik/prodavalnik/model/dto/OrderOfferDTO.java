package com.prodavalnik.prodavalnik.model.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderOfferDTO {
    private List<OrderOfferDetailsDTO> offersToOrder;

    private int totalOffersCount;

    private BigDecimal totalPrice;

    public OrderOfferDTO() {
        this.offersToOrder = new ArrayList<>();
    }

    public OrderOfferDTO(List<OrderOfferDetailsDTO> offersToOrder) {
        this.offersToOrder = offersToOrder;
    }

    public List<OrderOfferDetailsDTO> getOffersToOrder() {
        return offersToOrder;
    }

    public void setOffersToOrder(List<OrderOfferDetailsDTO> offersToOrder) {
        this.offersToOrder = offersToOrder;
    }

    public int getTotalOffersCount() {
        return this.offersToOrder.stream().map(OrderOfferDetailsDTO::getQuantity)
                .reduce(0,Integer::sum);
    }

    public void setTotalOffersCount(int totalOffersCount) {
        this.totalOffersCount = totalOffersCount;
    }

    public BigDecimal getTotalPrice() {
        return this.offersToOrder.stream()
                .map(OrderOfferDetailsDTO::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
