package com.example.repairagency.controller;


import com.example.repairagency.dto.AppUserRegistrationDto;
import com.example.repairagency.dto.UserAlreadyExistAuthenticationException;
import com.example.repairagency.service.AppUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
                                              AppUserRegistrationDto appUserRegistrationDto){

    try {
    appUserService.save(appUserRegistrationDto);
    } catch (UserAlreadyExistAuthenticationException exception){

    return "redirect:/registration?userExist";

     }
        return "redirect:/registration?success";

    }
}
