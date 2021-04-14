package com.coderbd.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class HomeController {
    /**
     * This method is now for temporaray purpose.
     * @return
     */
    @GetMapping
    public String showHomeMessage(){
        return "Fully Secured API";
    }
}
