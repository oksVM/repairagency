package com.example.repairagency.controller;

import com.example.repairagency.dto.AppUserRegistrationDto;
import com.example.repairagency.exception.UserAlreadyExistAuthenticationException;
import com.example.repairagency.service.AppUserService;
import com.example.repairagency.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('admin')")
public class AdminController {

    private AppUserService appUserService;
    private OrderService orderService;

    @Autowired
    public AdminController(AppUserService appUserService, OrderService orderService) {
        this.appUserService = appUserService;
        this.orderService = orderService;
    }

    @GetMapping()
    public String main(){
        return "admin/adminHomepage";
    }

    @GetMapping("/master_registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new AppUserRegistrationDto());
        return "admin/masterRegistration";
    }


    @PostMapping("/master_registration")
    public String registerUserAccount(@ModelAttribute("user")
                                      @Valid AppUserRegistrationDto appUserRegistrationDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "admin/masterRegistration";

        try {
            appUserService.saveNewMaster(appUserRegistrationDto);
        } catch (UserAlreadyExistAuthenticationException exception) {
            bindingResult.rejectValue("email", "userData.email", "An account already exists for this email.");
            return "admin/masterRegistration";

        }
        return "redirect:/admin/master_registration?success";

    }

    @GetMapping("/customers")
    public String allCustomers(Model model){
        model.addAttribute("customers", appUserService.findAllCustomers());
        return "admin/customers";
    }

    @GetMapping("customers/deposit/{id}")
    public String depositForm(@PathVariable("id") Long id, Model model){
        model.addAttribute("customer", appUserService.findById(id));
        return "admin/customerDeposit";
    }

    //TODO exception
    @PostMapping ("customers/deposit/{id}")
    public String addMoneyToDeposit(@Min(1) @RequestParam("money") Integer money, @PathVariable("id") Long id){
        //if(bindingResult.hasErrors()) eroor with that field
        // return "customer/update_deposit";
        appUserService.updateDeposit(money,id);
        //return "redirect:/admin/customers/deposit/{id}";
        return "redirect:/admin/customers";
    }

    @GetMapping("/orders")
    public String allOrders(Model model){
        model.addAttribute("orderlist", orderService.findAllOrders());
        return "admin/allOrders";
    }

}

