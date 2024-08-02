package com.prodavalnik.prodavalnik.init;

import com.prodavalnik.prodavalnik.model.entity.Shop;
import com.prodavalnik.prodavalnik.model.enums.ShopEnum;
import com.prodavalnik.prodavalnik.repository.ShopRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Arrays;

@Component
public class ShopInit implements CommandLineRunner {
    private final ShopRepository shopRepository;

    public ShopInit(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }


    @Override
    public void run(String... args) {

        if (this.shopRepository.count() == 0) {

            Arrays.stream(ShopEnum.values())
                    .forEach(shopCity -> {
                        Shop shop = new Shop();
                        shop.setCity(shopCity);

                        switch (shopCity) {
                            case PLOVDIV -> {
                                shop.setDescription("In Plovdiv, the thrift store offers unique vintage finds that will take you back in time. We have also private parking area for our customers!");
                                shop.setAddress("ul. Perushtitsa 8, 4000 Plovdiv, Bulgaria");
                                shop.setPhoneNumber("089 362 7405");
                                shop.setEmail("prodavai_plovdiv@abv.bg");
                                shop.setOpen(LocalTime.of(8, 0));
                                shop.setClose(LocalTime.of(20, 0));
                                shop.setImageUrl("/images/plovdiv/plovdiv.jpg");
                            }
                            case SOFIA -> {
                                shop.setDescription("Here you not only sells second-hand goods but can also visit monthly art events and creative workshops.");
                                shop.setAddress("bul. Tsarigradsko Shose 115z, 1784 Sofia, Bulgaria");
                                shop.setPhoneNumber("089 900 8484");
                                shop.setEmail("prodavai_sofia@abv.bg");
                                shop.setOpen(LocalTime.of(8, 0));
                                shop.setClose(LocalTime.of(22, 0));
                                shop.setImageUrl("/images/sofia/sofia.jpg");

                            }
                            case BURGAS -> {
                                shop.setDescription("The shop here is famous for its rich collection of antiques and unusual items. It is a great place to discover second-hand goods and new products.");
                                shop.setAddress("ul. Yanko Komitov 6, 8000 Burgas, Bulgaria");
                                shop.setPhoneNumber("089 200 2424");
                                shop.setEmail("prodavai_burgas@abv.bg");
                                shop.setOpen(LocalTime.of(8, 0));
                                shop.setClose(LocalTime.of(20, 0));
                                shop.setImageUrl("/images/burgas/burgas.jpg");
                            }
                        }

                        this.shopRepository.saveAndFlush(shop);
                    });
        }
    }
}
