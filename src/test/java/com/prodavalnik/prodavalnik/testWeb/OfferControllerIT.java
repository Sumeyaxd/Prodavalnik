package com.prodavalnik.prodavalnik.testWeb;

import com.prodavalnik.prodavalnik.model.dto.AddOfferDTO;
import com.prodavalnik.prodavalnik.model.dto.OffersViewDTO;
import com.prodavalnik.prodavalnik.service.OfferService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@SpringBootTest
@AutoConfigureMockMvc
class OfferControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfferService offerService;

    @Test
    @WithMockUser(username = "user", roles = {"USER", "ADMINISTRATOR"})
    void testAddDish_Get() throws Exception {
        mockMvc.perform(get("/offers/add-offer"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-offer"))
                .andExpect(model().attributeExists("addOfferDTO"));
    }


    @Test
    @WithMockUser(username = "user", roles = {"USER", "ADMINISTRATOR"})
    void testAddOffer_Post_ValidationError() throws Exception {
        mockMvc.perform(post("/offers/add-offer")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "")
                        .param("description", "")
                        .param("price", "-1.0")
                        .param("imageUrl", "https://offer.jpg")
                        .param("category", "CLOTHES")
                        .param("restaurant", "PLOVDIV")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("add-offer"))
                .andExpect(model().attributeExists("addOfferDTO"))
                .andExpect(model().attributeHasFieldErrors("addOfferDTO", "name", "description", "price"));
    }



    @Test
    @WithMockUser(username = "user")
    void testViewAll() throws Exception {
        OffersViewDTO offersViewDTO = new OffersViewDTO();
        offersViewDTO.setClothes(Collections.emptyList());
        when(offerService.getAllOffers()).thenReturn(offersViewDTO);

        mockMvc.perform(get("/offers/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("all"))
                .andExpect(model().attributeExists("offers"))
                .andExpect(model().attribute("offers", offersViewDTO));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER", "ADMINISTRATOR"})
    void testDeleteOffer() throws Exception {
        mockMvc.perform(delete("/offers/all/delete-offer/{id}", 1L)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/offers/all"));

        verify(offerService).deleteOffer(1L);
    }

}
