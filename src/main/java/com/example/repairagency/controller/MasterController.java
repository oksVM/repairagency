package com.example.repairagency.controller;

import com.example.repairagency.exception.NotEnoughMoneyException;
import com.example.repairagency.model.Order;
import com.example.repairagency.service.AppUserService;
import com.example.repairagency.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/master")
@PreAuthorize("hasAuthority('master')")
@Slf4j
public class MasterController {

    AppUserService appUserService;
    OrderService orderService;

    @Autowired
    public MasterController(AppUserService appUserService, OrderService orderService) {
        this.appUserService = appUserService;
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public String viewAllOrders(Model model){
        return allOrdersPaginated(1, model);
    }

    @GetMapping("/orders/page/{pageNo}")
    public String allOrdersPaginated(@PathVariable(value = "pageNo") int pageNo, Model model){
        int pageSize = 5;

        Page<Order> page = orderService.findAllCurrentMasterOrders(pageNo, pageSize);
        List<Order> orderList = page.getContent();

        model.addAttribute("currentPage", pageNo );
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("orderList", orderList);

        return "master/orders";
    }

    @GetMapping("order/{id}")
    public String orderProcessing(@PathVariable("id") Long id, Model model){
        model.addAttribute("order", orderService.findOrderById(id));

        return "master/order";
    }

    @PostMapping("order/inwork/{id}")
    public String takeInWork(@PathVariable("id") Long id)  {
        orderService.takeInWork(id);
        log.info("Order with id = {} has been taken in work", id);
        return "redirect:/master/order/{id}?successInWork";
    }

    @PostMapping("order/done/{id}")
    public String markAsDOne(@PathVariable("id") Long id) {
        orderService.markAsDone(id);
        log.info("Order with id = {} is completed", id);
        return "redirect:/master/order/{id}?successDone";
    }
}
