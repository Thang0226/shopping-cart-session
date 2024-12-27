package com.controller;

import com.model.Cart;
import com.model.Product;
import com.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@SessionAttributes("cart")
public class ProductController {
    @Autowired
    private IProductService productService;

    @ModelAttribute("cart")
    public Cart setupCart() {
        return new Cart();
    }

    @GetMapping("/shop")
    public ModelAndView showShop() {
        ModelAndView modelAndView = new ModelAndView("shop");
        modelAndView.addObject("products", productService.findAll());
        return modelAndView;
    }

    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable Long id,
                            @ModelAttribute Cart cart,
                            @RequestParam("action") Optional<String> action) {
        Optional<Product> productOptional = productService.findById(id);
        if (!productOptional.isPresent()) {
            return "error_404";
        }
        if (action.isPresent()) {
            if (action.get().equals("show")) {
                cart.addProduct(productOptional.get());
                return "redirect:/shopping-cart";
            }
        }
        cart.addProduct(productOptional.get());
        return "redirect:/shop";
    }

    @GetMapping("/subtract/{id}")
    public String subtractFromCart(@PathVariable Long id,
                            @ModelAttribute Cart cart,
                            @RequestParam("action") Optional<String> action) {
        Optional<Product> productOptional = productService.findById(id);
        if (!productOptional.isPresent()) {
            return "error_404";
        }
        cart.subtractProduct(productOptional.get());
        if (action.isPresent()) {
            if (action.get().equals("show")) {
                return "redirect:/shopping-cart";
            }
        }
        return "redirect:/shop";
    }
}
