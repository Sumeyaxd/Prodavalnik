package com.example.demo.web;


import com.example.demo.model.dto.AddOfferDTO;
import com.example.demo.model.dto.OffersViewDTO;
import com.example.demo.service.OfferService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/dishes")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/add-offer")
    public ModelAndView addDish(Model model) {

        if (!model.containsAttribute("addOfferDTO")) {
            model.addAttribute("addOfferDTO", new AddOfferDTO());
        }

        return new ModelAndView("add-offer");
    }

    @PostMapping("/add-dish")
    public ModelAndView addDish(@Valid @ModelAttribute("addDishDTO") AddOfferDTO addOfferDTO,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addDishDTO", addOfferDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.addDishDTO",
                            bindingResult);

            return new ModelAndView("add-dish");
        }

        boolean isAdded = this.offerService.addOffer(addOfferDTO);

        if (isAdded) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "Successfully added new dish to menu!");

            return new ModelAndView("redirect:/dishes/menu");
        }

        return new ModelAndView("add-dish");
    }


    @DeleteMapping("/menu/delete-dish/{id}")
    public ModelAndView deleteDish(@PathVariable("id") Long id) {

        this.offerService.deleteOffer(id);

        return new ModelAndView("redirect:/offers");
    }
}