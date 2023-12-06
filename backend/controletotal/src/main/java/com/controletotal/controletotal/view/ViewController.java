package com.controletotal.controletotal.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ViewController {

    @GetMapping("/")
    public String menu() {
        return "index";
    }

    @GetMapping("/fornecedor/menu")
    public String fornecedoresMenu() {
        return "gerenciarFornecedores";
    }

    
    @GetMapping("/usuario/menu")
    public String usuariosMenu() {
        return "gerenciarUsuarios";
    }

    @GetMapping("/item/menu")
    public String itensMenu() {
        return "gerenciarItens";
    }
}
