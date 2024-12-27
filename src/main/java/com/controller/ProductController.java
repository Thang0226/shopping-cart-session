package com.controller;

import com.model.Cart;
import com.model.Product;
import com.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String showShop(Model model) {
        model.addAttribute("products", productService.findAll());
        return "shop";
    }

    @GetMapping("/view/{id}")
    public ModelAndView showView(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        ModelAndView modelAndView = new ModelAndView("view_product");
        modelAndView.addObject("product", product.get());
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
