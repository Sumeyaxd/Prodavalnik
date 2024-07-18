package com.example.demo.web;

import com.example.demo.model.dto.ShopDetailsDTO;
import com.example.demo.model.enums.ShopEnum;
import com.example.demo.service.ShopService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/shop")
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }


    @GetMapping("/plovdiv")
    public ModelAndView showPlovdivInfo(Model model) {

        ModelAndView modelAndView = new ModelAndView("plovdiv");

        ShopDetailsDTO shopDetailsDTO = this.shopService.getShopDetails(ShopEnum.PLOVDIV);

        modelAndView.addObject("shop", shopDetailsDTO);

        return modelAndView;
    }

    @GetMapping("/burgas")
    public ModelAndView showBurgasInfo() {

        ModelAndView modelAndView = new ModelAndView("burgas");

        ShopDetailsDTO shopDetailsDTO = this.shopService.getShopDetails(ShopEnum.BURGAS);

        modelAndView.addObject("shop", shopDetailsDTO);

        return modelAndView;
    }

    @GetMapping("/sofia")
    public ModelAndView showSofiaInfo() {

        ModelAndView modelAndView = new ModelAndView("plovdiv");

        ShopDetailsDTO shopDetailsDTO = this.shopService.getShopDetails(ShopEnum.SOFIA);

        modelAndView.addObject("shop", shopDetailsDTO);

        return modelAndView;
    }
}