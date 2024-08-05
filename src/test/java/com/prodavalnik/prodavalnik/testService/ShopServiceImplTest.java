package com.prodavalnik.prodavalnik.testService;

import com.prodavalnik.prodavalnik.model.dto.ShopDetailsDTO;
import com.prodavalnik.prodavalnik.model.entity.Shop;
import com.prodavalnik.prodavalnik.model.enums.ShopEnum;
import com.prodavalnik.prodavalnik.repository.ShopRepository;
import com.prodavalnik.prodavalnik.service.impl.ShopServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ShopServiceImplTest {
    private ShopServiceImpl shopServiceImpl;

    @Mock
    private ShopRepository mockShopRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        this.shopServiceImpl = new ShopServiceImpl(mockShopRepository, modelMapper);
    }

    @Test
    public void testGetShopDetails_Found() {
        Shop shop = new Shop();
        shop.setCity(ShopEnum.PLOVDIV);

        ShopDetailsDTO restaurantDetailsDTO = new ShopDetailsDTO();
        restaurantDetailsDTO.setCity(ShopEnum.PLOVDIV);

        when(mockShopRepository.findByCity(ShopEnum.PLOVDIV)).thenReturn(Optional.of(shop));
        when(modelMapper.map(shop, ShopDetailsDTO.class)).thenReturn(restaurantDetailsDTO);

        ShopDetailsDTO foundRestaurantDetails = shopServiceImpl.getShopDetails(ShopEnum.PLOVDIV);

        assertEquals(restaurantDetailsDTO, foundRestaurantDetails);
    }

    @Test
    public void testGetShopDetails_NotFound() {
        Shop shop = new Shop();

        when(mockShopRepository.findByCity(shop.getCity())).thenReturn(Optional.empty());

        ShopDetailsDTO foundRestaurantDetails = shopServiceImpl.getShopDetails(shop.getCity());

        assertNull(foundRestaurantDetails);
    }

    @Test
    public void testFindByName_Found() {
        Shop shop = new Shop();
        shop.setCity(ShopEnum.PLOVDIV);

        when(mockShopRepository.findByCity(ShopEnum.PLOVDIV)).thenReturn(Optional.of(shop));

        Optional<Shop> foundShop = shopServiceImpl.findByCity(ShopEnum.PLOVDIV);

        assertEquals(ShopEnum.PLOVDIV, foundShop.get().getCity());
    }

    @Test
    public void testFindByName_NotFound() {
        Shop shop = new Shop();

        when(mockShopRepository.findByCity(shop.getCity())).thenReturn(Optional.empty());

        Optional<Shop> foundShop = shopServiceImpl.findByCity(shop.getCity());

        assertFalse(foundShop.isPresent());
    }
}
