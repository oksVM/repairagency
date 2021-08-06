package com.example.repairagency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/v1/startpage")
public class GreetingController {

    @GetMapping()
    public String main(){
        return "main";
    }
}
