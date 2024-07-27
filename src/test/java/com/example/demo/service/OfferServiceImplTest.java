package com.example.demo.service;

import com.example.demo.model.dto.AddOfferDTO;
import com.example.demo.model.dto.OfferDetailsDTO;
import com.example.demo.model.dto.OffersViewDTO;
import com.example.demo.model.entity.*;
import com.example.demo.model.enums.CategoryEnum;
import com.example.demo.model.enums.RoleEnum;
import com.example.demo.model.enums.ShopEnum;
import com.example.demo.repository.OfferRepository;
import com.example.demo.service.impl.OfferServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class OfferServiceImplTest {

    @Mock
    private OfferRepository mockOfferRepository;

    @Mock
    private ShopService mockShopService;

    @Mock
    private CategoryService mockCategoryService;

    @Mock
    private UserService mockUserService;

    @Mock
    private ModelMapper modelMapper;

    private OfferServiceImpl testOfferServiceImpl;

    @BeforeEach
    public void setUp() {
        testOfferServiceImpl = new OfferServiceImpl(mockOfferRepository, mockShopService,
                mockCategoryService, mockUserService, modelMapper);
    }

    @Test
    public void testAddOffer() {

        Shop shop = new Shop();
        shop.setCity(ShopEnum.PLOVDIV);
        Category category = new Category();
        category.setName(CategoryEnum.CLOTHES);

        AddOfferDTO addOfferDTO = new AddOfferDTO();
        addOfferDTO.setCity(shop.getCity());
        addOfferDTO.setCategory(category.getName());

        Offer offer = new Offer();

        when(mockShopService.findByCity(ShopEnum.PLOVDIV)).thenReturn(Optional.of(shop));
        when(mockCategoryService.findByName(CategoryEnum.CLOTHES)).thenReturn(Optional.of(category));
        when(modelMapper.map(addOfferDTO, Offer.class)).thenReturn(offer);
        when(mockOfferRepository.saveAndFlush(offer)).thenReturn(offer);

        boolean result = testOfferServiceImpl.addOffer(addOfferDTO);

        assertTrue(result);
        assertEquals(offer.getShop(), shop);
        assertEquals(offer.getCategory(), category);
        verify(mockOfferRepository, times(1)).saveAndFlush(offer);
    }

    @Test
    public void testAddOffer_Failure_NullDTO() {
        boolean result = testOfferServiceImpl.addOffer(null);

        assertFalse(result);
        verify(mockOfferRepository, times(0)).saveAndFlush(any(Offer.class));
    }

    @Test
    public void testAddOffer_Failure_CategoryOrShopNotFound() {
        AddOfferDTO addOfferDTO = new AddOfferDTO();

        when(mockShopService.findByCity(addOfferDTO.getCity())).thenReturn(Optional.empty());
        when(mockCategoryService.findByName(addOfferDTO.getCategory())).thenReturn(Optional.empty());

        boolean result = testOfferServiceImpl.addOffer(addOfferDTO);

        assertFalse(result);
        verify(mockOfferRepository, times(0)).saveAndFlush(any(Offer.class));
    }

    @Test
    public void testDeleteOffer_Success() {
        Offer offer = new Offer();
        User adminUser = new User();

        Role admin = new Role();
        admin.setRole(RoleEnum.ADMINISTRATOR);

        adminUser.setRoles(Collections.singletonList(admin));
        adminUser.setUsername("admin");

        when(mockOfferRepository.findById(anyLong())).thenReturn(Optional.of(offer));
        when(mockUserService.getLoggedUsername()).thenReturn("admin");
        when(mockUserService.findUserByUsername("admin")).thenReturn(Optional.of(adminUser));

        testOfferServiceImpl.deleteOffer(1L);

        verify(mockOfferRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteOffer_Failure_NotAdmin() {
        Offer offer = new Offer();
        User testUser = new User();

        Role user = new Role();
        user.setRole(RoleEnum.USER);

        testUser.setRoles(Collections.singletonList(user));
        testUser.setUsername("testUser");

        when(mockOfferRepository.findById(anyLong())).thenReturn(Optional.of(offer));
        when(mockUserService.getLoggedUsername()).thenReturn("testUser");
        when(mockUserService.findUserByUsername("testUser")).thenReturn(Optional.of(testUser));

        testOfferServiceImpl.deleteOffer(1L);

        verify(mockOfferRepository, times(0)).deleteById(1L);
    }

    @Test
    public void testDeleteOffer_Failure_DishNotFound() {
        User adminUser = new User();

        Role admin = new Role();
        admin.setRole(RoleEnum.ADMINISTRATOR);

        adminUser.setRoles(Collections.singletonList(admin));
        adminUser.setUsername("admin");

        when(mockOfferRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(mockUserService.getLoggedUsername()).thenReturn("admin");
        when(mockUserService.findUserByUsername("admin")).thenReturn(Optional.of(adminUser));

        testOfferServiceImpl.deleteOffer(1L);

        verify(mockOfferRepository, times(0)).deleteById(1L);
    }

    @Test
    public void testFindOfferById_Success() {
        Offer offer = new Offer();

        when(mockOfferRepository.findById(1L)).thenReturn(Optional.of(offer));

        Optional<Offer> result = testOfferServiceImpl.findOfferById(1L);

        assertTrue(result.isPresent());
        assertEquals(offer, result.get());
    }

    @Test
    public void testFindOfferById_Failure() {
        when(mockOfferRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Offer> result = testOfferServiceImpl.findOfferById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    public void testMapOfferToDTO() {
        Category category = new Category();
        category.setName(CategoryEnum.CLOTHES);
        Shop shop = new Shop();
        shop.setCity(ShopEnum.PLOVDIV);

        Offer offer1 = new Offer();
        offer1.setCategory(category);
        offer1.setShop(shop);
        Offer offer2 = new Offer();
        offer2.setCategory(category);
        offer2.setShop(shop);

        when(mockOfferRepository.findAll()).thenReturn(Stream.of(offer1, offer2).collect(Collectors.toList()));
        when(modelMapper.map(any(Offer.class), eq(OfferDetailsDTO.class))).thenAnswer(invocation -> {
            Offer offer = invocation.getArgument(0);
            OfferDetailsDTO dto = new OfferDetailsDTO();
            dto.setCity(offer.getShop().getCity());

            return dto;
        });

        List<OfferDetailsDTO> result = testOfferServiceImpl.mapOfferToDTO(CategoryEnum.CLOTHES);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(shop.getCity(), result.get(0).getCity());
        assertEquals(shop.getCity(), result.get(1).getCity());
    }

    @Test
    public void testGetAllOffers() {
        Offer clothes = createOffer(CategoryEnum.CLOTHES, ShopEnum.PLOVDIV);
        Offer electronics = createOffer(CategoryEnum.ELECTRONICS, ShopEnum.SOFIA);
        Offer furniture = createOffer(CategoryEnum.FURNITURE, ShopEnum.BURGAS);


        List<Offer> offers = Arrays.asList(clothes, electronics, furniture);

        when(mockOfferRepository.findAll()).thenReturn(offers);
        when(modelMapper.map(any(Offer.class), eq(OfferDetailsDTO.class))).thenAnswer(invocation -> {
            Offer offer = invocation.getArgument(0);
            OfferDetailsDTO dto = new OfferDetailsDTO();
            dto.setName(offer.getName());
            dto.setCity(offer.getShop().getCity());

            return dto;
        });

        OffersViewDTO offersViewDTO = testOfferServiceImpl.getAllOffers();

        assertNotNull(offersViewDTO);
        assertEquals(1, offersViewDTO.getClothes().size());
        assertEquals(1, offersViewDTO.getElectronics().size());
        assertEquals(1, offersViewDTO.getFurniture().size());

        assertEquals(ShopEnum.PLOVDIV, offersViewDTO.getClothes().get(0).getCity());
        assertEquals(ShopEnum.SOFIA, offersViewDTO.getElectronics().get(0).getCity());
        assertEquals(ShopEnum.BURGAS, offersViewDTO.getFurniture().get(0).getCity());

        verify(mockOfferRepository, times(3)).findAll();
        verify(modelMapper, times(3)).map(any(Offer.class), eq(OfferDetailsDTO.class));
    }

    private Offer createOffer(CategoryEnum categoryEnum, ShopEnum shopEnum) {
        Offer offer = new Offer();
        offer.setName(categoryEnum.name() + " Offer");

        Category category = new Category();
        category.setName(categoryEnum);
        offer.setCategory(category);

        Shop shop = new Shop();
        shop.setCity(shopEnum);
        offer.setShop(shop);

        return offer;

    }

}
