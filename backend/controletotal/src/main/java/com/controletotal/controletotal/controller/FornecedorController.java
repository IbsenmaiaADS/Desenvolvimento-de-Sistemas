package com.controletotal.controletotal.controller;

import com.controletotal.controletotal.dto.*;
import com.controletotal.controletotal.entity.Fornecedor;
import com.controletotal.controletotal.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@Validated
@RequestMapping("fornecedores")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "fornecedores", description = "Gerenciar fornecedores")
public class FornecedorController {
    private final FornecedorService fornecedorService;

    @GetMapping("/cadastro")
    public String formularioCadastro(Model model){
        FornecedorDto fornecedor = new FornecedorDto();
        model.addAttribute("fornecedor", fornecedor);
        return "cadastroFornecedor";
    }

    @GetMapping("/lista")
    public ModelAndView listaView() {
    ModelAndView mv = new ModelAndView("listaFornecedores");
    List<Fornecedor> fornecedor = fornecedorService.buscaTodosOsFornecedores();
    if( !fornecedor.isEmpty() ) {
      mv.addObject("fornecedor", fornecedor);
      return mv;
    }
      return mv;
    }

    @GetMapping
    @Operation(summary = "Busca todos os fornecedores")
    public ResponseEntity<List<Fornecedor>> buscarTodosOsFornecedores() {
        List<Fornecedor> fornecedores = fornecedorService.buscaTodosOsFornecedores();
        return ResponseEntity.ok(fornecedores);
    }

    @GetMapping("/buscar")
    @Operation(summary = "Busca fornecedores por id ou nome")
    public ResponseEntity<Fornecedor> buscarFornecedorPorNomeOuId(@RequestParam(required = false) Long id,
                                                            @RequestParam(required = false) String nome) {
        return ResponseEntity.ok(fornecedorService.buscaFornecedor(id, nome));
    }

    @PostMapping("/cadastrar/save")
    @Operation(summary = "Cadastra fornecedores")
    public String cadastrarFornecedor(@ModelAttribute("fornecedor") @Valid FornecedorDto fornecedorDto, BindingResult result, Model model) {
        if (fornecedorService.buscaFornecedorPeloNome(fornecedorDto.getNome()) != null) {
            result.rejectValue("nome", null, "Já existe um fornecedor registrado com esse nome.");
        } 

        if (result.hasErrors()) {
            model.addAttribute("fornecedor", fornecedorDto);
            return "cadastroFornecedor";
        }

        ResponseEntity.status(HttpStatus.CREATED).body(fornecedorService.cadastraFornecedor(fornecedorDto));
        return "redirect:/fornecedores/cadastro?success";
    }


    @PatchMapping("/atualizar/{id}")
    @Operation(summary = "Edita fornecedores")
    public ResponseEntity<Fornecedor> atualizarFornecedor(
            @PathVariable
            Long id,
            @RequestParam(required = false)
            String nome,
            @RequestParam(required = false)
            String numTelefone) {
        return ResponseEntity.ok(fornecedorService.atualizaFornecedor(id, nome, numTelefone));
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deleta fornecedores")
    @ResponseStatus(HttpStatus.OK)
    public void deletarFornecedor(@PathVariable Long id) {
        fornecedorService.deletaFornecedor(id);
    }

    @GetMapping("/itens")
    @Operation(summary = "Listar itens de um fornecedor")
    public ResponseEntity<List<ItensFornecedorDto>> buscarItensFornecedor(
            @RequestParam(required = false)
            @NotNull(message = "É obrigatório informar o id do fornecedor")
            Long id
    ) {
        return ResponseEntity.ok(fornecedorService.buscarItens(id));
    }

}
