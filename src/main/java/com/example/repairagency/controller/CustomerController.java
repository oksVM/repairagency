package com.example.repairagency.controller;


import com.example.repairagency.exception.NotEnoughMoneyException;
import com.example.repairagency.model.AppUser;
import com.example.repairagency.model.Order;
import com.example.repairagency.model.Review;
import com.example.repairagency.service.AppUserService;
import com.example.repairagency.service.OrderService;
import com.example.repairagency.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.NoSuchElementException;

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
        return "customer/homepage";
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
    public String viewAllOrders(Model model){
        return allOrdersPaginated(1, model);
    }

    @GetMapping("/orders/page/{pageNo}")
    public String allOrdersPaginated(@PathVariable(value = "pageNo") int pageNo, Model model){
        int pageSize = 5;

        Page<Order> page = orderService.findAllCurrentCustomerOrders(pageNo, pageSize);
        List<Order> orderList = page.getContent();

        model.addAttribute("currentPage", pageNo );
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("orderList", orderList);

        return "customer/orders";
    }

    @GetMapping("order/{id}")
    public String orderProcessing(@PathVariable("id") Long id, Model model){
        try{
            model.addAttribute("order", orderService.findOrderById(id));
        } catch (NoSuchElementException u){
            //TODO
        }
        return "customer/order";
    }

    @PostMapping ("orders/payment/{id}")
    public String payForOrder(@PathVariable("id") Long id) throws NotEnoughMoneyException {
        //if(bindingResult.hasErrors()) eroor with that field
        // return "customer/update_deposit";
        orderService.payForOrder(id);
        return "redirect:/customer/order/{id}";
    }

    @GetMapping("/update_deposit")
    public String addMoneyToDeposit (Model model){
        model.addAttribute("customer", appUserService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        return "customer/deposit";
    }

    //TODO excaption handler
    @PostMapping("/update_deposit")
    public String addMoneyToDeposit(@Min(1) @RequestParam("money") Integer money){
        //if(bindingResult.hasErrors()) eroor with that field
           // return "customer/update_deposit";
        appUserService.updateDeposit(money,((AppUser) appUserService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName())).getId() );
        return "redirect:/customer/update_deposit";
    }

    @PostMapping("/orders/feedback/{id}")
    public String leaveFeedback(@RequestParam("feedback") String feedback,  @PathVariable("id") Long orderId){
        appUserService.leaveFeedback (feedback, orderId);
        return "redirect:/customer/order/{id}";
    }

}
