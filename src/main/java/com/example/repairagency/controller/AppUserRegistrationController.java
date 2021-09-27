package com.example.repairagency.controller;


import com.example.repairagency.dto.AppUserRegistrationDto;
import com.example.repairagency.exception.UserAlreadyExistAuthenticationException;
import com.example.repairagency.service.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
@Slf4j
public class AppUserRegistrationController {

    private final AppUserService appUserService;

    public AppUserRegistrationController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new AppUserRegistrationDto());
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user")
                                      @Valid AppUserRegistrationDto appUserRegistrationDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.error("Invalid values in user registration form");
            return "registration";
        }

        try {
            appUserService.saveNewCustomer(appUserRegistrationDto);
        } catch (UserAlreadyExistAuthenticationException exception) {
            log.error("User with email = {} already exist", appUserRegistrationDto.getEmail());
            model.addAttribute("alreadyExist", true);
            return "registration";
        }
        return "redirect:/registration?success";
    }
}

