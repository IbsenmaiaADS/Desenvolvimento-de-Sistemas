package com.controletotal.controletotal.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ViewController {

    @GetMapping("/")
    public ModelAndView menu() {
        return new ModelAndView("index");
    }

    @GetMapping("/fornecedor/menu")
    public ModelAndView fornecedoresMenu() {
        ModelAndView mv = new ModelAndView("gerenciarFornecedores");
        return mv;    }

    
    @GetMapping("/usuario/menu")
    public ModelAndView usuariosMenu() {
        ModelAndView mv = new ModelAndView("gerenciarUsuarios");
        return mv;    }

    @GetMapping("/compra/menu")
    public ModelAndView compraMenu() {
        ModelAndView mv = new ModelAndView("gerenciarCompras");
        return mv;
    }

    @GetMapping("/item/menu")
    public ModelAndView itensMenu() {
        ModelAndView mv = new ModelAndView("gerenciarItens");
        return mv;
    }

    @GetMapping("/solicitacoesEstoque/menu")
    public ModelAndView estoqueMenu() {
        ModelAndView mv = new ModelAndView("solicitacoesEstoque");
        return mv;
    }
    
    @GetMapping("/solicitacoesEstoque/solicitar")
    public ModelAndView solicitarItem() {
        ModelAndView mv = new ModelAndView("solicitarItem");
        return mv;    } 

    @GetMapping("/solicitacoesEstoque/gerenciar")
    public ModelAndView gerenciarSolicitacoes() {
        ModelAndView mv = new ModelAndView("gerenciarEstoque");
        return mv;    }  
}
