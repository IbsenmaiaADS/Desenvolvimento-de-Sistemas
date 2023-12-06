package com.controletotal.controletotal.view;

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
