package com.example.repairagency.controller;


import com.example.repairagency.dto.AppUserRegistrationDto;
import com.example.repairagency.exception.UserAlreadyExistAuthenticationException;
import com.example.repairagency.service.AppUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class AppUserRegistrationController {

    private AppUserService appUserService;

    public AppUserRegistrationController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/1")
    @PreAuthorize("hasAuthority('customers:write')")
    public String getAll() {
        return "hello";
    }


    @GetMapping("/2")
    @PreAuthorize("hasAuthority('customers:read')")
    //@PreAuthorize("hasAuthority('customers:write')")
    public String getCustomerById() {

        return "hello";
    }
    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new AppUserRegistrationDto());
        return "registration";
    }


    @PostMapping
    public String registerUserAccount(@ModelAttribute("user")
                                      @Valid AppUserRegistrationDto appUserRegistrationDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "registration";

        try {
            appUserService.save(appUserRegistrationDto);
        } catch (UserAlreadyExistAuthenticationException exception) {
            bindingResult.rejectValue("email", "userData.email", "An account already exists for this email.");
            return "registration";

        }
        return "redirect:/registration?success";

    }
}

