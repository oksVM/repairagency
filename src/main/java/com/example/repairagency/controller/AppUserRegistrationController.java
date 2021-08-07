package com.example.repairagency.controller;


import com.example.repairagency.dto.AppUserRegistrationDto;
import com.example.repairagency.dto.UserAlreadyExistAuthenticationException;
import com.example.repairagency.service.AppUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class AppUserRegistrationController {

    private AppUserService appUserService;

    public AppUserRegistrationController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }


    @GetMapping
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new AppUserRegistrationDto());
        return "registration";
    }



   /* @PostMapping
    public String registerUserAccount(@ModelAttribute("user")
                                      AppUserRegistrationDto appUserRegistrationDto){

        appUserService.save(appUserRegistrationDto);
        return "redirect:/registration?success";

    }*/

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user")
                                              @Valid AppUserRegistrationDto appUserRegistrationDto, BindingResult bindingResult, Model model){

    try {
    appUserService.save(appUserRegistrationDto);
    } catch (UserAlreadyExistAuthenticationException exception){
        bindingResult.rejectValue("email", "userData.email","An account already exists for this email.");
        model.addAttribute("registrationForm", appUserRegistrationDto);
        return "registration";

    //return "redirect:/registration?userExist";

     }
        return "redirect:/registration?success";

    }
}



/*  @GetMapping
    public String register(final Model model){
        model.addAttribute("userData", new UserData());
        return "account/register";
    }

    @PostMapping
    public String userRegistration(final @Valid  UserData userData, final BindingResult bindingResult, final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("registrationForm", userData);
            return "account/register";
        }
        try {
            userService.register(userData);
        }catch (UserAlreadyExistException e){
            bindingResult.rejectValue("email", "userData.email","An account already exists for this email.");
            model.addAttribute("registrationForm", userData);
            return "account/register";
        }
        model.addAttribute("registrationMsg", messageSource.getMessage("user.registration.verification.email.msg", null, LocaleContextHolder.getLocale()));
        return "account/register";
    }
*/
