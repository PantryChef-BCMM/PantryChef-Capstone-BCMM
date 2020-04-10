package com.pantrychef.pantrychef.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PantryChefSearchController {

    @GetMapping("/pantrychef")
    public String pantrychef(){
        return "api/pantryChefSearch";
    }
}
