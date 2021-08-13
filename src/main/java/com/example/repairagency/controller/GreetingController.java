package com.example.repairagency.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("starthomepage")
public class GreetingController {

    @GetMapping()
    public String main(){
        return "startPage";
    }
}
