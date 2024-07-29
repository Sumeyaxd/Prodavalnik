package com.example.demo.web;

import com.example.demo.model.dto.ShopDetailsDTO;
import com.example.demo.model.entity.Shop;
import com.example.demo.model.enums.ShopEnum;
import com.example.demo.service.ShopService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@AutoConfigureMockMvc
public class ShopControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopService restaurantService;

    @Test
    void testShowPlovdivInfo() throws Exception {
        ShopDetailsDTO shopDetailsDTO = new ShopDetailsDTO();
        shopDetailsDTO.setCity(ShopEnum.PLOVDIV);

        when(restaurantService.getShopDetails(ShopEnum.PLOVDIV)).thenReturn(shopDetailsDTO);

        mockMvc.perform(get("/shops/plovdiv"))
                .andExpect(status().isOk())
                .andExpect(view().name("plovdiv"))
                .andExpect(model().attributeExists("shop"))
                .andExpect(model().attribute("shop", shopDetailsDTO));
    }

    @Test
    void testShowSofiaInfo() throws Exception {
        ShopDetailsDTO shopDetailsDTO = new ShopDetailsDTO();
        shopDetailsDTO.setCity(ShopEnum.SOFIA);

        when(restaurantService.getShopDetails(ShopEnum.SOFIA)).thenReturn(shopDetailsDTO);

        mockMvc.perform(get("/shops/sofia"))
                .andExpect(status().isOk())
                .andExpect(view().name("sofia"))
                .andExpect(model().attributeExists("shop"))
                .andExpect(model().attribute("shop", shopDetailsDTO));
    }

    @Test
    void testShowBurgasInfo() throws Exception {
        ShopDetailsDTO shopDetailsDTO = new ShopDetailsDTO();
        shopDetailsDTO.setCity(ShopEnum.BURGAS);

        when(restaurantService.getShopDetails(ShopEnum.BURGAS)).thenReturn(shopDetailsDTO);

        mockMvc.perform(get("/shops/burgas"))
                .andExpect(status().isOk())
                .andExpect(view().name("burgas"))
                .andExpect(model().attributeExists("shop"))
                .andExpect(model().attribute("shop", shopDetailsDTO));
    }
}
