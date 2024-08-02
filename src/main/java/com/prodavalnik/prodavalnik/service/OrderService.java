package com.prodavalnik.prodavalnik.service;

import com.prodavalnik.prodavalnik.model.dto.AddOrderDTO;
import com.prodavalnik.prodavalnik.model.dto.OrderOfferDTO;
import com.prodavalnik.prodavalnik.model.dto.OrdersViewDTO;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    void addToCart(Long id, int quantity);

    OrderOfferDTO getAllOffersInCart();

    void removeFromCart(Long id);

    boolean makeOrder(AddOrderDTO addOrderDTO, BigDecimal totalPrice);

    void deleteOrder(Long id);

    List<OrdersViewDTO> getAllOrders();

    boolean progressOrder(Long id);
}
