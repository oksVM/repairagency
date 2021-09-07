package com.example.repairagency.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class LoginController {

    @GetMapping("login")
    public String login(){
        return "login";}

    @GetMapping("/")
    public String home(){
        return "redirect:/homepage";
    }

    @GetMapping("/homepage")
    public String homePage(){
        return "homepage";
    }
}

