package com.example.demo.web;


import com.example.demo.model.dto.AddOrderDTO;
import com.example.demo.model.dto.OffersViewDTO;
import com.example.demo.model.dto.OrderOfferDTO;

import com.example.demo.model.dto.OrdersViewDTO;
import com.example.demo.service.OfferService;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OfferService offerService;
    private final UserService userService;

    public OrderController(OrderService orderService, OfferService offerService, UserService userService) {
        this.orderService = orderService;
        this.offerService = offerService;
        this.userService = userService;
    }

    @GetMapping("/make-order")
    public ModelAndView viewMakeOrder(Model model) {

        if (!model.containsAttribute("addOrderDTO")) {
            model.addAttribute("addOrderDTO", new AddOrderDTO());
        }

        ModelAndView modelAndView = new ModelAndView("make-order");

        OrderOfferDTO orderOfferDTO = this.orderService.getAllDishesInCart();
        String username = this.userService.getLoggedUsername();

        modelAndView.addObject("username", username);
        modelAndView.addObject("cartDishes", orderOfferDTO);

        return modelAndView;
    }

    @PostMapping("/make-order/{totalPrice}")
    public ModelAndView makeOrder(@ModelAttribute("addOrderDTO") AddOrderDTO addOrderDTO,
                                  @PathVariable("totalPrice") BigDecimal totalPrice,
                                  RedirectAttributes redirectAttributes) {

        if (totalPrice.compareTo(BigDecimal.ZERO) <= 0) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Please, add some dishes to your shopping cart!");

            return new ModelAndView("redirect:/orders/make-order");
        }

        boolean isMadeOrder = this.orderService.makeOrder(addOrderDTO, totalPrice);

        return new ModelAndView("redirect:/home");
    }

    @DeleteMapping("/delete-order/{id}")
    public ModelAndView deleteOrder(@PathVariable("id") Long id) {

        this.orderService.deleteOrder(id);

        return new ModelAndView("redirect:/home");
    }

    @PostMapping("/add-to-cart/{id}")
    public ModelAndView addToCart(@PathVariable("id") Long id,
                                  @RequestParam(value = "quantity", defaultValue = "1") int quantity) {

        this.orderService.addToCart(id, quantity);

        return new ModelAndView("redirect:/offers/all");
    }

    @GetMapping("/remove-from-cart/{id}")
    public String removeFromCart(@PathVariable("id") Long id) {

        this.orderService.removeFromCart(id);

        return "redirect:/orders/make-order";
    }

    @GetMapping
    public ModelAndView getAllOrders() {

        ModelAndView modelAndView = new ModelAndView("orders");

        List<OrdersViewDTO> ordersViewDTO = this.orderService.getAllOrders();

        modelAndView.addObject("orders", ordersViewDTO);
        modelAndView.addObject("ordersCount", ordersViewDTO.size());

        return modelAndView;
    }

    @PostMapping("/progress-order/{id}")
    public ModelAndView progressOrder(@PathVariable("id") Long id,
                                      RedirectAttributes redirectAttributes) {

        boolean isProgressed = this.orderService.progressOrder(id);

        if (!isProgressed) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "You cannot progress already delivered order!");
        }

        return new ModelAndView("redirect:/orders");
    }

    @GetMapping("/all")
    public ModelAndView viewAll() {

        ModelAndView modelAndView = new ModelAndView("all");

        OffersViewDTO offersViewDTO = this.offerService.getAllOffers();

        modelAndView.addObject("offers", offersViewDTO);

        return modelAndView;
    }

    @DeleteMapping("/all/delete-offer/{id}")
    public ModelAndView deleteDish(@PathVariable("id") Long id) {

        this.offerService.deleteOffer(id);

        return new ModelAndView("redirect:/offers/all");
    }

}