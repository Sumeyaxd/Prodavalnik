package com.prodavalnik.prodavalnik.testWeb;

import com.prodavalnik.prodavalnik.model.dto.AddOrderDTO;
import com.prodavalnik.prodavalnik.model.dto.OrderOfferDTO;
import com.prodavalnik.prodavalnik.model.dto.OrdersViewDTO;
import com.prodavalnik.prodavalnik.service.OrderService;
import com.prodavalnik.prodavalnik.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "user")
    public void testViewMakeOrder() throws Exception {
        OrderOfferDTO orderOffers = new OrderOfferDTO();
        when(orderService.getAllOffersInCart()).thenReturn(orderOffers);
        when(userService.getLoggedUsername()).thenReturn("testuser");

        mockMvc.perform(get("/orders/make-order"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("addOrderDTO"))
                .andExpect(model().attribute("username", "testuser"))
                .andExpect(model().attribute("cartOffers", orderOffers))
                .andExpect(view().name("make-order"));
    }

    @Test
    @WithMockUser(username = "user")
    public void testMakeOrder_ValidData() throws Exception {
        when(orderService.makeOrder(any(AddOrderDTO.class), any(BigDecimal.class))).thenReturn(true);

        mockMvc.perform(post("/orders/make-order/{totalPrice}", "100")
                        .param("deliveryAddress", "Correct Address")
                        .param("phoneNumber", "111222333")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/home"));
    }

    @Test
    @WithMockUser(username = "user")
    public void testMakeOrder_InvalidTotalPrice() throws Exception {
        mockMvc.perform(post("/orders/make-order/{totalPrice}", "0")
                        .param("deliveryAddress", "Correct Address")
                        .param("phoneNumber", "111222333")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("errorMessage"))
                .andExpect(view().name("redirect:/orders/make-order"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER", "ADMINISTRATOR"})
    public void testDeleteOrder() throws Exception {
        mockMvc.perform(delete("/orders/delete-order/{id}", 1L)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/home"));
    }

    @Test
    @WithMockUser(username = "user")
    public void testAddToCart() throws Exception {
        mockMvc.perform(post("/orders/add-to-cart/{id}", 1L)
                        .param("quantity", "2")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/offers/all"));
    }

    @Test
    @WithMockUser(username = "user")
    public void testRemoveFromCart() throws Exception {
        mockMvc.perform(get("/orders/remove-from-cart/{id}", 1L))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/orders/make-order"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER", "ADMINISTRATOR"})
    public void testGetAllOrders() throws Exception {
        List<OrdersViewDTO> ordersViewInfoList = new ArrayList<>();
        when(orderService.getAllOrders()).thenReturn(ordersViewInfoList);

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("orders"))
                .andExpect(model().attribute("ordersCount", ordersViewInfoList.size()))
                .andExpect(view().name("orders"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER", "ADMINISTRATOR"})
    public void testProgressOrder_ValidData() throws Exception {
        when(orderService.progressOrder(1L)).thenReturn(true);

        mockMvc.perform(post("/orders/progress-order/{id}", 1L)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/orders"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER", "ADMINISTRATOR"})
    public void testProgressOrder_InvalidData() throws Exception {
        when(orderService.progressOrder(1L)).thenReturn(false);

        mockMvc.perform(post("/orders/progress-order/{id}", 1L)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("errorMessage"))
                .andExpect(view().name("redirect:/orders"));
    }

}
