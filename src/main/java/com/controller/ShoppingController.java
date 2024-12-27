package com.controller;

import com.model.Cart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ShoppingController {
    @ModelAttribute("cart")
    public Cart setupCart(){
        return new Cart();
    }

    @GetMapping("/shopping-cart")
    public ModelAndView showCart (@SessionAttribute("cart") Cart cart){
        ModelAndView modelAndView = new ModelAndView("cart");
        modelAndView.addObject("cart",cart);
        return modelAndView;
    }

    @GetMapping("/checkout")
    public ModelAndView showCheckout (@SessionAttribute("cart") Cart cart){
        ModelAndView modelAndView = new ModelAndView("checkout");
        modelAndView.addObject("cart",cart);
        return modelAndView;
    }

    @GetMapping("/order")
    public String order(@SessionAttribute("cart") Cart cart){
        /* Do some payment thing
        ...
        */
        cart.clearCart();
        return "redirect:/shop";
    }
}
