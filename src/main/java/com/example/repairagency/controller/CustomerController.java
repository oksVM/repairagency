package com.example.repairagency.controller;


import com.example.repairagency.model.AppUser;
import com.example.repairagency.model.Order;
import com.example.repairagency.service.AppUserService;
import com.example.repairagency.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Controller
@Validated
@RequestMapping("/customer")
@PreAuthorize("hasAuthority('customer')")
public class CustomerController {
    AppUserService appUserService;
    OrderService orderService;

    @Autowired
    public CustomerController(AppUserService appUserService, OrderService orderService) {
        this.appUserService = appUserService;
        this.orderService = orderService;
    }

    @GetMapping
    public String customerStartPage (){
        return "customer/customerhomepage";
    }

    @GetMapping("/user_info")
    public String userInfo (Model model){
        model.addAttribute("user", appUserService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        return "customer/userinfo";
    }

    @GetMapping("/order/new")
    public String newOrderForm(Model model){
        model.addAttribute("order", new Order());
        return "customer/neworder";
    }

    @PostMapping("/order/save")
    public String newOrder (@ModelAttribute("order") Order order) {
            orderService.save(order);
        return "redirect:/customer/order/new";
    }

    @GetMapping("/orders")
    public String allOrders(Model model){
        model.addAttribute("order", orderService.findALlCurrentCustomerOrders());
        return "customer/currentcustomerorders";
    }

    @GetMapping("/update_deposit")
    public String addMoneyToDeposit (){
        return "customer/deposit";
    }

    //TODO excaption handler
    //@PostMapping("/{id}")
    @PostMapping("/update_deposit")
    public String addMoneyToDeposit(@Min(1) @RequestParam("money") Integer money){
        //if(bindingResult.hasErrors()) eroor with that field
           // return "customer/update_deposit";
        appUserService.updateDeposit(money);
        return "redirect:/customer/update_deposit";
    }
}
