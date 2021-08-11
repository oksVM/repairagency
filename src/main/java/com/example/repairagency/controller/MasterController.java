package com.example.repairagency.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/master")
@PreAuthorize("hasAuthority('master')")
public class MasterController {
    @GetMapping()
    public String main(){
        return "customerhomepage";
    }
}