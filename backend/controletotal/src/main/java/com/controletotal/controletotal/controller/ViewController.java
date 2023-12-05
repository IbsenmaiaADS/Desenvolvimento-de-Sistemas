package com.controletotal.controletotal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.controletotal.controletotal.service.FornecedorService;

import lombok.RequiredArgsConstructor;

import com.controletotal.controletotal.dto.FornecedorDto;
import com.controletotal.controletotal.entity.Fornecedor;

import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final FornecedorService fornecedorService;

    @GetMapping("/")
    public String menu() {
        return "index";
    }

    @GetMapping("/fornecedores/menu")
    public String fornecedores() {
        return "fornecedores";
    }
}
