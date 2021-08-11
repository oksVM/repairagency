package com.example.repairagency.controller;


import com.example.repairagency.model.Area;
import com.example.repairagency.model.Order;
import com.example.repairagency.service.AppUserService;
import com.example.repairagency.service.OrderService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/customer")
@PreAuthorize("hasAuthority('customer')")
public class CustomerController {
    AppUserService appUserService;
    OrderService orderService;
    Area area;
@Autowired
    public CustomerController(AppUserService appUserService, OrderService orderService) {
        this.appUserService = appUserService;
        this.orderService = orderService;
        //this.area = area;
    }

    @GetMapping("/order/new")
    public String newOrderForm(Model model){
        //List<Area> areaList = Area.getAll();

        model.addAttribute("order", new Order());
        //model.addAttribute("areaCategories", areaList);
        return "customer/neworder";
    }
    @PostMapping("/order/save")
    public String newOrder (@ModelAttribute("order") Order order) {

            orderService.save(order);

        return "redirect:/customer/order/new";

    }

    @GetMapping("/orders")
    public String allOrders(Model model){
        //List<Area> areaList = Area.getAll();
        List<Order> orderList = orderService.findOrdersAll(5L);
        model.addAttribute("order", orderList);
        //model.addAttribute("areaCategories", areaList);
        return "customer/customerhomepage";
    }



}
