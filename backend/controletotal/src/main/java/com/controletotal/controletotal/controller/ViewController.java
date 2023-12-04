package com.controletotal.controletotal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/menu")
    public String greeting() {
        return "index";
    }
}
