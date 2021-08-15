package com.example.repairagency.controller;

import com.example.repairagency.dto.AppUserRegistrationDto;
import com.example.repairagency.exception.UserAlreadyExistAuthenticationException;
import com.example.repairagency.model.Order;
import com.example.repairagency.service.AppUserService;
import com.example.repairagency.service.OrderService;
import com.example.repairagency.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

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

   /* @GetMapping()
    public String main(){
        return "admin/adminHomepage";
    }*/

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
        try{
        model.addAttribute("customer", appUserService.findById(id));} catch (UsernameNotFoundException u){
            System.out.println("hbfvgnhbgvfd");
        }
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

   /* @GetMapping("/orders")
    public String allOrders(Model model){
        model.addAttribute("orderlist", orderService.findAllOrders());
        return "admin/allOrders";
    }*/

    @GetMapping("/")
    public String viewAllOrders(Model model){
        return allOrdersPaginated(1, model);
    }

    @GetMapping("/page/{pageNo}")
    public String allOrdersPaginated(@PathVariable(value = "pageNo") int pageNo, Model model){
        int pageSize = 5;

        Page<Order> page = orderService.findAllOrdersPaginated(pageNo, pageSize);
        List<Order> orderList = page.getContent();

        model.addAttribute("currentPage", pageNo );
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("orderList", orderList);

       return "admin/allOrders";
    }

}

