package com.controletotal.controletotal.view;

import com.controletotal.controletotal.dto.FornecedorDto;
import com.controletotal.controletotal.entity.Fornecedor;
import com.controletotal.controletotal.handler.ErroDeNegocio;
import com.controletotal.controletotal.service.FornecedorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@Validated
@RequestMapping("fornecedor")
@RequiredArgsConstructor
@Tag(name = "fornecedorView", description = "Gerenciar view de fornecedor")
@CrossOrigin(origins = "*")
public class FornecedorViewController {
    private final FornecedorService fornecedorService;


    @GetMapping("/cadastrar")
    public ModelAndView formularioCadastro(){
        ModelAndView mv = new ModelAndView("cadastrarFornecedor");
        FornecedorDto fornecedor = new FornecedorDto();
        mv.addObject("fornecedor", fornecedor);
        return mv;
    }

    @PostMapping("/cadastrar")
    public ModelAndView cadastrarFornecedor(@RequestBody @ModelAttribute("fornecedor") @Valid FornecedorDto fornecedorDto, BindingResult result){
        ModelAndView erroAoCadastrar = new ModelAndView("cadastrarFornecedor");
        ModelAndView sucessoAoCadastrar = new ModelAndView("redirect:/fornecedor/cadastrar?success");


        if (fornecedorService.buscaFornecedorPeloNome(fornecedorDto.getNome()) != null) {
            result.rejectValue("nome", null, "Já existe um fornecedor registrado com esse nome.");
        } 

        if (result.hasErrors()) {
            erroAoCadastrar.addObject("fornecedor", fornecedorDto);
            return erroAoCadastrar;
        }

        ResponseEntity.status(HttpStatus.CREATED).body(fornecedorService.cadastraFornecedor(fornecedorDto)).getBody();
        return sucessoAoCadastrar;
    }

    @GetMapping("/listar")
    public ModelAndView listarFornecedor() {
    ModelAndView mv = new ModelAndView("fornecedores");
    List<Fornecedor> fornecedores = fornecedorService.buscaTodosOsFornecedores();
      mv.addObject("fornecedores", fornecedores);
      return mv;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarFornecedor(
        @PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("editarFornecedor");

        Fornecedor fornecedor = fornecedorService.buscaFornecedor(id, null);

        if(fornecedor != null) {
            mv.addObject("fornecedor", fornecedor);
        } else {
            throw new ErroDeNegocio("Fornecedor não encontrado com o ID: " + id);
        }

        return mv;
    }

    @PostMapping("/atualizar/{id}")
    public ModelAndView atualizarFornecedor(
    @PathVariable("id") Long id, 
    @Valid @ModelAttribute("fornecedor") FornecedorDto fornecedorDto, 
    BindingResult result) {

        ModelAndView erroAoEditar = new ModelAndView("editarFornecedor");
        ModelAndView sucessoAoEditar = new ModelAndView("redirect:/fornecedor/editar/{id}?success");

        if (result.hasErrors()) {
            erroAoEditar.addObject("fornecedor", fornecedorDto);
            return erroAoEditar;
        }

        fornecedorService.atualizaFornecedor(id, fornecedorDto);
        return sucessoAoEditar;
    }

    @GetMapping("/deletar/{id}")
    public ModelAndView deletarFornecedor(@PathVariable("id") long id) {
        ModelAndView sucessoAoDeletar = new ModelAndView("redirect:/fornecedor/listar?success");
        fornecedorService.deletaFornecedor(id);
        return sucessoAoDeletar;
    }
}