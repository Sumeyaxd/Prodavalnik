package com.prodavalnik.prodavalnik.web.rest;

import com.prodavalnik.prodavalnik.model.dto.AddSupplierDTO;
import com.prodavalnik.prodavalnik.model.dto.SupplierDTO;
import com.prodavalnik.prodavalnik.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
@Controller
@RequestMapping("/suppliers")
public class SupplierController {

        private final SupplierService supplierService;

        public SupplierController(SupplierService supplierService) {
            this.supplierService = supplierService;
        }

        @GetMapping
        public ModelAndView getAllSuppliers() {

            ModelAndView modelAndView = new ModelAndView("suppliers");

            List<SupplierDTO> suppliersDTO = this.supplierService.getAllSuppliers();

            modelAndView.addObject("suppliers", suppliersDTO);

            return modelAndView;
        }

        @GetMapping("/add-supplier")
        public ModelAndView addSupplier(Model model) {

            if (!model.containsAttribute("addSupplierDTO")) {
                model.addAttribute("addSupplierDTO", new AddSupplierDTO());
            }

            return new ModelAndView("add-supplier");
        }

        @PostMapping("/add-supplier")
        public ModelAndView addSupplier(@Valid @ModelAttribute("addSupplierDTO") AddSupplierDTO addSupplierDTO,
                                       BindingResult bindingResult, RedirectAttributes redirectAttributes) {

            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("addSupplierDTO", addSupplierDTO)
                        .addFlashAttribute("org.springframework.validation.BindingResult.addSupplierDTO",
                                bindingResult);

                return new ModelAndView("add-supplier");
            }

            this.supplierService.addSupplier(addSupplierDTO);

            return new ModelAndView("redirect:/suppliers");
        }

        @DeleteMapping("/delete-supplier/{id}")
        public ModelAndView deleteSupplier(@PathVariable("id") Long id) {

            this.supplierService.deleteSupplier(id);

            return new ModelAndView("redirect:/suppliers");
        }
    }
