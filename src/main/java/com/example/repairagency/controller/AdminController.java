package com.example.repairagency.controller;

import com.example.repairagency.dto.AppUserRegistrationDto;
import com.example.repairagency.exception.UserAlreadyExistAuthenticationException;
import com.example.repairagency.model.AppUser;
import com.example.repairagency.model.Order;
import com.example.repairagency.service.AppUserService;
import com.example.repairagency.service.OrderService;
import com.example.repairagency.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.NoSuchElementException;

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
        return allCustomersPaginated(1, model);
    }

    @GetMapping("/customers/page/{pageNo}")
    public String allCustomersPaginated(@PathVariable(value = "pageNo") int pageNo, Model model){
        int pageSize = 5;

        Page<AppUser> page = appUserService.findAllCustomersPaginated(pageNo, pageSize);
        List<AppUser> appUsersList = page.getContent();

        model.addAttribute("currentPage", pageNo );
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("appUsersList", appUsersList);

        return "admin/customers";
    }

    @GetMapping("customers/deposit/{id}")
    public String depositForm(@PathVariable("id") Long id, Model model){
        try{
        model.addAttribute("customer", appUserService.findById(id));
        } catch (UsernameNotFoundException u){
            //TODO
        }
        return "admin/customerDeposit";
    }

    //TODO exception
    @PostMapping ("customers/deposit/{id}")
    public String addMoneyToDeposit(@Min(1) @RequestParam("money") Integer money, @PathVariable("id") Long id){
        //if(bindingResult.hasErrors()) eroor with that field
        // return "customer/update_deposit";
        appUserService.updateDeposit(money,id);
        return "redirect:/admin/customers/deposit/{id}";
    }


    @GetMapping("/orders")
    public String viewAllOrders(Model model){
        String keyWord = null;

        return allOrdersPaginated( 1,"id", "asc", keyWord, model);
    }

    @GetMapping("/orders/page/{pageNo}")
    public String allOrdersPaginated(@PathVariable(value = "pageNo") int pageNo,
                                     @RequestParam("sortField") String sortField,
                                     @RequestParam("sortDir") String sortDir,@Param("keyWord") String keyWord,
                                     Model model){
        int pageSize = 5;

        Page<Order> page = orderService.findAllOrdersPaginated(keyWord, pageNo, pageSize, sortField,sortDir);
        List<Order> orderList = page.getContent();

        model.addAttribute("keyWord", keyWord);

        model.addAttribute("currentPage", pageNo );
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc")?"desc":"asc");

        model.addAttribute("orderList", orderList);

       return "admin/allOrders";
    }

    @GetMapping("orders/{id}")
    public String orderProcessing(@PathVariable("id") Long id, Model model){
        try{
            model.addAttribute("order", orderService.findOrderById(id));
            model.addAttribute("mastersList", appUserService.findAllMasters());
        } catch (NoSuchElementException u){
            //TODO
        }
        return "admin/order";
    }

    @PostMapping ("orders/setprice/{id}")
    public String setPrice(@Min(1) @RequestParam("price") Integer price, @PathVariable("id") Long id){
        //if(bindingResult.hasErrors()) eroor with that field
        // return "customer/update_deposit";
        orderService.setPrice(price,id);
        return "redirect:/admin/orders/{id}";
    }

    @PostMapping ("orders/setmaster/{id}")
    public String setMaster(@RequestParam("master") Long masterId, @PathVariable("id") Long id){
        //if(bindingResult.hasErrors()) eroor with that field
        // return "customer/update_deposit";
        System.out.println("-----"+masterId);
        orderService.setMaster(masterId,id);
        return "redirect:/admin/orders/{id}";
    }
    @PostMapping("/order/save")
    public String newOrder (@ModelAttribute("order") Order order) {
        orderService.save(order);
        return "redirect:/customer/order/new";
    }

    @GetMapping("/masters")
    public String allMasters(Model model){
        return allMastersPaginated(1, model);
    }

    @GetMapping("/masters/page/{pageNo}")
    public String allMastersPaginated(@PathVariable(value = "pageNo") int pageNo, Model model){
        int pageSize = 5;

        Page<AppUser> page = appUserService.findAllMastersPaginated(pageNo, pageSize);
        List<AppUser> mastersList = page.getContent();

        model.addAttribute("currentPage", pageNo );
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("mastersList", mastersList);

        return "admin/masters";
    }

    @GetMapping("masters/reviews/{id}")
    public String showReviews(@PathVariable("id") Long id, Model model){
        try{
            model.addAttribute("master", appUserService.findById(id));
        } catch (UsernameNotFoundException u){
            //TODO
        }
        return "admin/reviews";
    }

}

