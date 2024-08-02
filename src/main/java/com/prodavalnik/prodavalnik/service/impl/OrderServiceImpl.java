package com.prodavalnik.prodavalnik.service.impl;

import com.prodavalnik.prodavalnik.model.dto.*;
import com.prodavalnik.prodavalnik.model.entity.Offer;
import com.prodavalnik.prodavalnik.model.entity.Order;
import com.prodavalnik.prodavalnik.model.entity.User;
import com.prodavalnik.prodavalnik.model.enums.OrderStatusEnum;
import com.prodavalnik.prodavalnik.repository.OrderRepository;
import com.prodavalnik.prodavalnik.service.OfferService;
import com.prodavalnik.prodavalnik.service.OrderService;
import com.prodavalnik.prodavalnik.service.UserService;
import com.prodavalnik.prodavalnik.service.events.MakeOrder;
import com.prodavalnik.prodavalnik.service.exception.BadOrderRequestException;
import com.prodavalnik.prodavalnik.service.exception.DeleteObjectException;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final OrderRepository orderRepository;
    private final OfferService offerService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private Map<Long, Integer> offersToOrder;

    public OrderServiceImpl(ApplicationEventPublisher applicationEventPublisher, OrderRepository orderRepository,
                            OfferService offerService, UserService userService, ModelMapper modelMapper) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.orderRepository = orderRepository;
        this.offerService = offerService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.offersToOrder = new HashMap<>();
    }

    @Override
    public void addToCart(Long id, int quantity) {

        if (quantity >= 1) {
            this.offersToOrder.put(id, quantity);
        }
    }

    @Override
    public void removeFromCart(Long id) {
        Optional<Offer> optionalOffer = this.offerService.findOfferById(id);

        if (optionalOffer.isPresent()) {

            this.offersToOrder.remove(id);
        }
    }

    @Override
    public boolean makeOrder(AddOrderDTO addOrderDTO, BigDecimal totalPrice) {

        String deliveryAddress = addOrderDTO.getDeliveryAddress();
        String phoneNumber = addOrderDTO.getPhoneNumber();

        if (deliveryAddress == null || deliveryAddress.length() < 3 || deliveryAddress.length() > 100) {
            throw new BadOrderRequestException("delivery address");
        }

        if (phoneNumber == null || phoneNumber.length() < 7 || phoneNumber.length() > 15) {
            throw new BadOrderRequestException("phone number");
        }

        if (totalPrice.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        String username = this.userService.getLoggedUsername();

        Optional<User> optionalUser = this.userService.findUserByUsername(username);

        if (optionalUser.isEmpty()) {
            return false;
        }

        User user = optionalUser.get();

        Order order = this.mapToOrder(user, totalPrice, addOrderDTO);

        this.orderRepository.saveAndFlush(order);

        this.applicationEventPublisher.publishEvent(
                new MakeOrder(this, user.getEmail(), user.getFullName(), order.getId(),
                        order.getTotalPrice(), order.getDeliveryAddress(), order.getPhoneNumber()));

        return true;
    }

    @Override
    public void deleteOrder(Long id) {

        String username = this.userService.getLoggedUsername();

        Optional<Order> optionalOrder = this.orderRepository.findById(id);
        Optional<User> optionalUser = this.userService.findUserByUsername(username);

        if (optionalOrder.isPresent() && optionalUser.isPresent()) {

            Order order = optionalOrder.get();

            if (order.getStatus().equals(OrderStatusEnum.DELIVERED) && order.getClient().getUsername().equals(username)) {
                this.orderRepository.deleteById(id);
            } else {
                throw new DeleteObjectException("You cannot delete order until it is not delivered yet!");
            }

        } else {
            throw new DeleteObjectException("You cannot delete order with id " + id + "!");
        }
    }

    @Override
    public List<OrdersViewDTO> getAllOrders() {

        return this.orderRepository.findAll().stream()
                .map(order -> {
                    OrdersViewDTO dto = this.modelMapper.map(order, OrdersViewDTO.class);
                    dto.setOrderedBy(order.getClient().getUsername());
                    dto.setOrderedOn(order.parseDateToString(order.getOrderedOn()));

                    String deliveredOn = (order.getDeliveredOn() == null)
                            ? "-"
                            : order.parseDateToString(order.getDeliveredOn());

                    dto.setDeliveredOn(deliveredOn);
                    dto.setStatus(order.getStatus().name());

                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public boolean progressOrder(Long id) {

        Optional<Order> optionalOrder = this.orderRepository.findById(id);

        if (optionalOrder.isPresent()) {

            Order order = optionalOrder.get();

            if (order.getStatus().equals(OrderStatusEnum.IN_PROGRESS)) {
                order.setStatus(OrderStatusEnum.DELIVERED);
                order.setDeliveredOn(LocalDateTime.now());
                this.orderRepository.save(order);

                return true;
            }
        }

        return false;
    }

    @Override
    public OrderOfferDTO getAllOffersInCart() {
        List<OrderOfferDetailsDTO> offersToOrderList = new ArrayList<>();

        for (Map.Entry<Long, Integer> entry : this.offersToOrder.entrySet()) {

            Long id = entry.getKey();
            int quantity = entry.getValue();

            Optional<Offer> optionalOffer = this.offerService.findOfferById(id);

            if (optionalOffer.isPresent()) {

                Offer offer = optionalOffer.get();

                OrderOfferDetailsDTO offerDetailsDTO = this.modelMapper.map(offer, OrderOfferDetailsDTO.class);
                offerDetailsDTO.setQuantity(quantity);
                offerDetailsDTO.setTotalPrice(offer.getPrice().multiply(BigDecimal.valueOf(quantity)));

                offersToOrderList.add(offerDetailsDTO);
            }
        }

        return new OrderOfferDTO(offersToOrderList);

    }

    private Order mapToOrder(User user, BigDecimal totalPrice, AddOrderDTO addOrderDTO) {
        Order order = this.modelMapper.map(addOrderDTO, Order.class);

        List<OrderOfferDetailsDTO> offersToOrder = new ArrayList<>();

        this.getAllOffersInCart().getOffersToOrder()
                .forEach(orderOfferDetailsDTO -> {
                    int quantity = orderOfferDetailsDTO.getQuantity();

                    for (int i = 1; i <= quantity; i++) {
                        offersToOrder.add(orderOfferDetailsDTO);
                    }
                });

        List<Offer> offers = offersToOrder.stream()
                .map(orderOfferDetailsDTO ->
                        this.modelMapper.map(orderOfferDetailsDTO, Offer.class)).toList();

        order.setTotalPrice(totalPrice);
        order.setOrderedOn(LocalDateTime.now());
        order.setClient(user);
        order.setStatus(OrderStatusEnum.IN_PROGRESS);
        order.setOffers(offers);

        return order;
    }
}
