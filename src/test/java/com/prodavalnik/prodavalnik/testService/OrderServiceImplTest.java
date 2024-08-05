package com.prodavalnik.prodavalnik.testService;

import com.prodavalnik.prodavalnik.model.dto.AddOrderDTO;
import com.prodavalnik.prodavalnik.model.dto.OrderOfferDTO;
import com.prodavalnik.prodavalnik.model.dto.OrderOfferDetailsDTO;
import com.prodavalnik.prodavalnik.model.dto.OrdersViewDTO;
import com.prodavalnik.prodavalnik.model.entity.Offer;
import com.prodavalnik.prodavalnik.model.entity.Order;
import com.prodavalnik.prodavalnik.model.entity.User;
import com.prodavalnik.prodavalnik.model.enums.OrderStatusEnum;
import com.prodavalnik.prodavalnik.repository.OrderRepository;
import com.prodavalnik.prodavalnik.service.OfferService;
import com.prodavalnik.prodavalnik.service.UserService;
import com.prodavalnik.prodavalnik.service.events.MakeOrder;
import com.prodavalnik.prodavalnik.service.exception.BadOrderRequestException;
import com.prodavalnik.prodavalnik.service.exception.DeleteObjectException;
import com.prodavalnik.prodavalnik.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class OrderServiceImplTest {
    private OrderServiceImpl orderServiceToTest;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private OrderRepository mockOrderRepository;

    @Mock
    private OfferService mockOfferService;

    @Mock
    private UserService mockUserService;

    @Mock
    private ModelMapper mockModelMapper;

    @BeforeEach
    public void setUp() {
        orderServiceToTest = new OrderServiceImpl(applicationEventPublisher, mockOrderRepository,
                mockOfferService, mockUserService, mockModelMapper);
    }


    @Test
    public void testAddToCart_InvalidQuantity() {
        Long offerId = 1L;
        int quantity = 0;

        Offer offer = new Offer();
        offer.setId(offerId);

        boolean result = orderServiceToTest.addToCart(offerId, quantity);

        assertFalse(result);
    }

    @Test
    public void testRemoveFromCart_OfferNotPresent() {
        Long offerId = 999L;
        int quantity = 3;

        orderServiceToTest.addToCart(offerId, quantity);

        when(mockOfferService.findOfferById(offerId)).thenReturn(Optional.empty());

        boolean result = orderServiceToTest.removeFromCart(offerId);

        assertFalse(result);
    }

    @Test
    public void testMakeOrder_Successful() {
        AddOrderDTO addOrderDTO = new AddOrderDTO();
        addOrderDTO.setDeliveryAddress("Test Address");
        addOrderDTO.setPhoneNumber("08888888");
        BigDecimal totalPrice = new BigDecimal("20.00");

        User user = new User();
        user.setUsername("testUsername");

        when(mockUserService.getLoggedUsername()).thenReturn("testUsername");
        when(mockUserService.findUserByUsername("testUsername")).thenReturn(Optional.of(user));
        when(mockOrderRepository.saveAndFlush(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mockModelMapper.map(any(AddOrderDTO.class), eq(Order.class))).thenAnswer(invocation -> {
            AddOrderDTO source = invocation.getArgument(0);
            Order order = new Order();
            order.setDeliveryAddress(source.getDeliveryAddress());
            order.setPhoneNumber(source.getPhoneNumber());

            return order;
        });

        boolean result = orderServiceToTest.makeOrder(addOrderDTO, totalPrice);

        assertTrue(result);
        verify(mockOrderRepository, times(1)).saveAndFlush(any(Order.class));
        verify(applicationEventPublisher, times(1)).publishEvent(any(MakeOrder.class));
    }

    @Test
    public void testMakeOrder_NullPrice() {
        AddOrderDTO addOrderDTO = new AddOrderDTO();
        addOrderDTO.setDeliveryAddress("Some Address");
        addOrderDTO.setPhoneNumber("0888888");
        BigDecimal totalPrice = new BigDecimal("0");

        boolean result = orderServiceToTest.makeOrder(addOrderDTO, totalPrice);

        assertFalse(result);
        verify(mockOrderRepository, times(0)).saveAndFlush(any(Order.class));
        verify(applicationEventPublisher, times(0)).publishEvent(any(MakeOrder.class));
    }

    @Test
    public void testMakeOrder_NullDeliveryAddress() {
        AddOrderDTO addOrderDTO = new AddOrderDTO();
        addOrderDTO.setDeliveryAddress("");
        addOrderDTO.setPhoneNumber("08888888");
        BigDecimal totalPrice = new BigDecimal("100");

        BadOrderRequestException exception = assertThrows(BadOrderRequestException.class, () ->
                orderServiceToTest.makeOrder(addOrderDTO, totalPrice));

        assertEquals("delivery address", exception.getParameter());
        verify(mockOrderRepository, times(0)).saveAndFlush(any(Order.class));
        verify(applicationEventPublisher, times(0)).publishEvent(any(MakeOrder.class));
    }

    @Test
    public void testMakeOrder_NullPhoneNumber() {
        AddOrderDTO addOrderDTO = new AddOrderDTO();
        addOrderDTO.setDeliveryAddress("Test Address");
        addOrderDTO.setPhoneNumber("");
        BigDecimal totalPrice = new BigDecimal("100");

        BadOrderRequestException exception = assertThrows(BadOrderRequestException.class, () ->
                orderServiceToTest.makeOrder(addOrderDTO, totalPrice));

        assertEquals("phone number", exception.getParameter());
        verify(mockOrderRepository, times(0)).saveAndFlush(any(Order.class));
        verify(applicationEventPublisher, times(0)).publishEvent(any(MakeOrder.class));
    }


    @Test
    public void testDeleteOrder_Successful() {
        Long orderId = 1L;
        User user = new User();
        user.setUsername("testUsername");

        Order order = new Order();
        order.setId(orderId);
        order.setClient(user);
        order.setStatus(OrderStatusEnum.DELIVERED);

        when(mockUserService.getLoggedUsername()).thenReturn("testUsername");
        when(mockOrderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(mockUserService.findUserByUsername("testUsername")).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> orderServiceToTest.deleteOrder(orderId));
        verify(mockOrderRepository, times(1)).deleteById(orderId);
    }

    @Test
    public void testDeleteOrder_UserNotFound() {
        AddOrderDTO addOrderDTO = new AddOrderDTO();
        addOrderDTO.setDeliveryAddress("Test address");
        addOrderDTO.setPhoneNumber("0888888");
        BigDecimal totalPrice = new BigDecimal("100");

        boolean result = orderServiceToTest.makeOrder(addOrderDTO, totalPrice);

        assertFalse(result);
        verify(mockOrderRepository, times(0)).saveAndFlush(any(Order.class));
        verify(applicationEventPublisher, times(0)).publishEvent(any(MakeOrder.class));
    }

    @Test
    public void testDeleteOrder_OrderInProgress_ThrowsException() {
        Long orderId = 1L;
        User user = new User();
        user.setUsername("testUsername");

        Order order = new Order();
        order.setId(orderId);
        order.setClient(user);
        order.setStatus(OrderStatusEnum.IN_PROGRESS);

        when(mockUserService.getLoggedUsername()).thenReturn("testUsername");
        when(mockOrderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(mockUserService.findUserByUsername("testUsername")).thenReturn(Optional.of(user));

        Exception exception = assertThrows(DeleteObjectException.class, () ->
                orderServiceToTest.deleteOrder(orderId));

        String expectedMessage = "You cannot delete order until it is not delivered yet!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(mockOrderRepository, times(0)).deleteById(orderId);
    }

    @Test
    public void testDeleteOrder_OrderNotFound_ThrowsException() {
        Long orderId = 999L;

        when(mockUserService.getLoggedUsername()).thenReturn("testuser");
        when(mockOrderRepository.findById(orderId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(DeleteObjectException.class, () ->
                orderServiceToTest.deleteOrder(orderId));

        String expectedMessage = "You cannot delete order with id 999!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(mockOrderRepository, times(0)).deleteById(orderId);
    }

    @Test
    public void testGetAllOrders_Successful() {
        User client = new User();
        client.setUsername("client");

        Order order1 = new Order();
        order1.setClient(client);
        order1.setOrderedOn(LocalDateTime.of(2024, 7, 12, 13, 15));
        order1.setDeliveredOn(null);
        order1.setStatus(OrderStatusEnum.IN_PROGRESS);

        Order order2 = new Order();
        order2.setClient(client);
        order2.setOrderedOn(LocalDateTime.of(2024, 7, 15, 11, 11));
        order2.setDeliveredOn(LocalDateTime.of(2024, 7, 17, 10, 5));
        order2.setStatus(OrderStatusEnum.DELIVERED);

        List<Order> orders = Arrays.asList(order1, order2);

        OrdersViewDTO dto1 = new OrdersViewDTO();

        OrdersViewDTO dto2 = new OrdersViewDTO();

        when(mockOrderRepository.findAll()).thenReturn(orders);

        when(mockModelMapper.map(order1, OrdersViewDTO.class)).thenReturn(dto1);
        when(mockModelMapper.map(order2, OrdersViewDTO.class)).thenReturn(dto2);

        List<OrdersViewDTO> result = orderServiceToTest.getAllOrders();

        assertEquals(2, result.size());
        assertEquals("client", result.get(0).getOrderedBy());
        assertEquals("2024-07-12 13:15:00", result.get(0).getOrderedOn());
        assertEquals("-", result.get(0).getDeliveredOn());
        assertEquals("IN_PROGRESS", result.get(0).getStatus());

        assertEquals("client", result.get(1).getOrderedBy());
        assertEquals("2024-07-15 11:11:00", result.get(1).getOrderedOn());
        assertEquals("2024-07-17 10:05:00", result.get(1).getDeliveredOn());
        assertEquals("DELIVERED", result.get(1).getStatus());
    }

    @Test
    public void testProgressOrder_Successful() {
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(OrderStatusEnum.IN_PROGRESS);

        when(mockOrderRepository.findById(orderId)).thenReturn(Optional.of(order));

        boolean result = orderServiceToTest.progressOrder(orderId);

        assertTrue(result);
        assertEquals(OrderStatusEnum.DELIVERED, order.getStatus());
        verify(mockOrderRepository, times(1)).save(order);
    }

    @Test
    public void testProgressOrder_AlreadyDeliveredOrder() {
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(OrderStatusEnum.DELIVERED);

        when(mockOrderRepository.findById(orderId)).thenReturn(Optional.of(order));

        boolean result = orderServiceToTest.progressOrder(orderId);

        assertFalse(result);
        verify(mockOrderRepository, times(0)).save(order);
    }

    @Test
    public void testGetAllOffersInCart_Successful() {
        Long offerId = 1L;
        int quantity = 2;
        Offer offer = new Offer();
        offer.setId(offerId);
        offer.setPrice(new BigDecimal("20.00"));

        orderServiceToTest.addToCart(offerId, quantity);
        when(mockOfferService.findOfferById(offerId)).thenReturn(Optional.of(offer));
        when(mockModelMapper.map(any(Offer.class), eq(OrderOfferDetailsDTO.class))).thenAnswer(invocation -> {
            Offer source = invocation.getArgument(0);
            OrderOfferDetailsDTO dto = new OrderOfferDetailsDTO();
            dto.setId(source.getId());
            dto.setTotalPrice(source.getPrice());
            return dto;
        });

        OrderOfferDTO result = orderServiceToTest.getAllOffersInCart();

        assertEquals(1, result.getOffersToOrder().size());
        assertEquals(offerId, result.getOffersToOrder().get(0).getId());
        assertEquals(quantity, result.getOffersToOrder().get(0).getQuantity());
        assertEquals(new BigDecimal("40.00"), result.getOffersToOrder().get(0).getTotalPrice());
    }
}
