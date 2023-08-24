package com.controletotal.controletotal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EstoqueController {

    @GetMapping("teste")
    public String teste() {
        return "Estou funcionando!";
    }
}
