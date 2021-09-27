package com.example.repairagency.controller;


import com.example.repairagency.dto.DepositDto;
import com.example.repairagency.exception.NotEnoughMoneyException;
import com.example.repairagency.model.AppUser;
import com.example.repairagency.model.Order;
import com.example.repairagency.service.AppUserService;
import com.example.repairagency.service.OrderService;
import com.example.repairagency.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/customer")
@PreAuthorize("hasAuthority('customer')")
@Slf4j
public class CustomerController {
    private final AppUserService appUserService;
    private final OrderService orderService;
    private final ReviewService reviewService;

    @Autowired
    public CustomerController(AppUserService appUserService, OrderService orderService, ReviewService reviewService) {
        this.appUserService = appUserService;
        this.orderService = orderService;
        this.reviewService=reviewService;
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
        log.info("New order with name = {} is created", order.getOrderName());
        return "redirect:/customer/order/new?success";
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
            model.addAttribute("order", orderService.findOrderById(id));

        return "customer/order";
    }

    @PostMapping ("orders/payment/{id}")
    public String payForOrder(@PathVariable("id") Long id){
        try{
            orderService.payForOrder(id);
            } catch (NotEnoughMoneyException | SQLException exception){
            log.error("Unsuccessful payment for order with id = {}", id);
            return "redirect:/customer/order/{id}?errorPayment";
        }
        log.error("Order with id = {} have been paid", id);
        return "redirect:/customer/order/{id}?successPayment";
    }

    @GetMapping("/update_deposit")
    public String addMoneyToDeposit (Model model){
        model.addAttribute("money", new DepositDto());
        model.addAttribute("customer", appUserService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        return "customer/deposit";
    }

    @PostMapping("/update_deposit")
    public String addMoneyToDeposit(@ModelAttribute("money") @Valid DepositDto depositDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("{} is incorrect values for amount of money", depositDTO.getAmountOfMoney());
            return "redirect:/customer/update_deposit?error";
        }
        appUserService.updateDeposit(depositDTO,((AppUser) appUserService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName())).getId());
        log.info("To current customer deposit was added {} ", depositDTO.getAmountOfMoney());
        return "redirect:/customer/update_deposit?success";
    }

    @PostMapping("/orders/feedback/{id}")
    public String leaveFeedback(@RequestParam("feedback") String feedback,  @PathVariable("id") Long orderId){
        reviewService.leaveFeedback (feedback, orderId);
        log.info("Review = {} was submitted for order with id = {}", feedback, orderId);
        return "redirect:/customer/order/{id}?successReview";
    }

}
