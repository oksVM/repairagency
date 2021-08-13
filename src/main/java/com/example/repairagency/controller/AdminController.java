package com.example.repairagency.controller;

import com.example.repairagency.dto.AppUserRegistrationDto;
import com.example.repairagency.exception.UserAlreadyExistAuthenticationException;
import com.example.repairagency.model.AppUser;
import com.example.repairagency.service.AppUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public AdminController(AppUserService appUserService) {
        this.appUserService = appUserService;
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
//////////////
    @GetMapping("/customers")
    public String allOrders(Model model){
        model.addAttribute("customers", appUserService.findAllCustomers());
        return "admin/customers";
    }

   /* @GetMapping("/update_deposit")
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
    }*/


    @GetMapping("customers/deposit/{id}")
    public String depositForm(@PathVariable("id") Long id, Model model){
        model.addAttribute("customer", appUserService.findById(id));
        return "admin/customerDeposit";
    }


    //TODO
    @PostMapping ("customers/deposit/{id}")
    public String addMoneyToDeposit(@Min(1) @RequestParam("money") Integer money, @PathVariable("id") Long id){
        //if(bindingResult.hasErrors()) eroor with that field
        // return "customer/update_deposit";
        appUserService.updateDeposit(money,id);
        //return "redirect:/admin/customers/deposit/{id}";
        return "redirect:/admin/customers";
    }
/*
    public String update(@ModelAttribute("person") @Valid Person person,BindingResult bindingResult, @PathVariable("id") int id){
        if(bindingResult.hasErrors())
            return "people/edit";
        personDAO.update(id, person);
        return "redirect:/customers/deposit/{id}/edit";
    }
<form th:method="PATCH" th:action="@{/admin/customers/deposit/{id}(id=${person.getId()})}" th:object="${person}">
  <label for="name">Enter name: </label>
  <input type="text" th:field="*{name}" id="name">
  <br/>
  <label for="age">Enter age: </label>
  <input type="text" th:field="*{age}" id="age">
  <br/>
  <label for="email">Enter email: </label>
  <input type="text" th:field="*{email}" id="email">
  <br/>
  <input type="submit" value="Update!">
</form>*/


}

