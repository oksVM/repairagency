package com.example.repairagency.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Main {

    @GetMapping("login")
    public String login(){
        //TODO probleme with already existen email. Hundle exception

        return "login";
    }

    @GetMapping("/")
    public String home(){
        return "index";
    }
}
