package com.example.repairagency.controller;

import com.example.repairagency.Message;
import com.example.repairagency.repository.MessageRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("so")
public class GreetingController {

    @Autowired
    private MessageRep messageRep;

    @GetMapping
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name,
                           Model model) {
        model.addAttribute("name", name);
        return "greeting";

    }

    @GetMapping("/main")
    public String main(Model model){
        model.addAttribute("welcome", "Welcome to our Repair Agency");
        return "main";
    }

   @GetMapping("/maino")
   public String main1(Model model){
       model.addAttribute("messages", messageRep.findAll());
       return "maino";
    }

    @PostMapping
    public String add (@ModelAttribute("message") Message message){
        messageRep.save(message);
        return "main";

    }


}
