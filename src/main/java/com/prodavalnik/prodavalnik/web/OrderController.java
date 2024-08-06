package com.prodavalnik.prodavalnik.web;


import com.prodavalnik.prodavalnik.model.dto.AddOrderDTO;
import com.prodavalnik.prodavalnik.model.dto.OffersViewDTO;
import com.prodavalnik.prodavalnik.model.dto.OrderOfferDTO;
import com.prodavalnik.prodavalnik.model.dto.OrdersViewDTO;
import com.prodavalnik.prodavalnik.service.OfferService;
import com.prodavalnik.prodavalnik.service.OrderService;
import com.prodavalnik.prodavalnik.service.UserService;
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

        OrderOfferDTO orderOfferDTO = this.orderService.getAllOffersInCart();
        String username = this.userService.getLoggedUsername();

        modelAndView.addObject("username", username);
        modelAndView.addObject("cartOffers", orderOfferDTO);

        return modelAndView;
    }

    @PostMapping("/make-order/{totalPrice}")
    public ModelAndView makeOrder(@ModelAttribute("addOrderDTO") AddOrderDTO addOrderDTO,
                                  @PathVariable("totalPrice") BigDecimal totalPrice,
                                  RedirectAttributes redirectAttributes) {

        if (totalPrice.compareTo(BigDecimal.ZERO) <= 0) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Please, add some offers to your shopping cart!");

            return new ModelAndView("redirect:/orders/make-order");
        }

        boolean isMadeOrder = this.orderService.makeOrder(addOrderDTO, totalPrice);

        return new ModelAndView("redirect:/home");
    }

    @PostMapping("/delete-order/{id}")
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

    @PostMapping("/all/delete-offer/{id}")
    public ModelAndView deleteOffer(@PathVariable("id") Long id) {

        this.offerService.deleteOffer(id);

        return new ModelAndView("redirect:/offers/all");
    }

}