package com.example.repairagency.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("starthomepage")
@Slf4j
public class GreetingController {

    @GetMapping()
    public String main(){
        return "startPage";
    }
}
