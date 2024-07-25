package com.example.demo.service;

import com.example.demo.model.dto.AddOrderDTO;
import com.example.demo.model.dto.OrderOfferDTO;
import com.example.demo.model.dto.OrdersViewDTO;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    void addToCart(Long id, int quantity);

    OrderOfferDTO getAllDishesInCart();

    void removeFromCart(Long id);

    boolean makeOrder(AddOrderDTO addOrderDTO, BigDecimal totalPrice);

    void deleteOrder(Long id);

    List<OrdersViewDTO> getAllOrders();

    boolean progressOrder(Long id);
}
