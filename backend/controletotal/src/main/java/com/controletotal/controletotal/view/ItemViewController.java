package com.controletotal.controletotal.view;

import com.controletotal.controletotal.dto.ItemDto;
import com.controletotal.controletotal.entity.Item;
import com.controletotal.controletotal.entity.ItemFornecedor;
import com.controletotal.controletotal.handler.ErroDeNegocio;
import com.controletotal.controletotal.service.ItemService;
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
@RequestMapping("item")
@RequiredArgsConstructor
@Tag(name = "itemView", description = "Gerenciar view de item")
@CrossOrigin(origins = "*")
public class ItemViewController {
    private final ItemService itemService;


    @GetMapping("/cadastrar")
    public ModelAndView formularioCadastro(){
        ModelAndView mv = new ModelAndView("cadastrarItem");
        ItemDto item = new ItemDto();
        mv.addObject("item", item);
        return mv;
    }

    @GetMapping("/solicitar")
    public ModelAndView solicitacaoItem(){
        ModelAndView mv = new ModelAndView("solicitarItem");
        return mv;
    }

    @PostMapping("/cadastrar")
    public ModelAndView cadastrarItem(@RequestBody @ModelAttribute("item") @Valid ItemDto itemDto, BindingResult result){
        ModelAndView erroAoCadastrar = new ModelAndView("cadastrar");
        ModelAndView sucessoAoCadastrar = new ModelAndView("redirect:/item/cadastrar?success");


        if (itemService.buscaItemPeloNome(itemDto.getNome()) != null) {
            result.rejectValue("nome", null, "Já existe um item registrado com esse nome.");
        } 

        if (result.hasErrors()) {
            erroAoCadastrar.addObject("item", itemDto);
            return erroAoCadastrar;
        }

        ResponseEntity.status(HttpStatus.CREATED).body(itemService.cadastraItem(itemDto)).getBody();
        return sucessoAoCadastrar;
    }

    @GetMapping("/listar")
    public ModelAndView listarItem() {
    ModelAndView mv = new ModelAndView("itens");
    List<Item> itens = itemService.buscaTodosOsItens();
    List<ItemFornecedor> itemFornecedor = itemService.buscaTodosOsItensRelacionadosComFornecedor();
      mv.addObject("itens", itens);
     mv.addObject("itemFornecedor", itemFornecedor);
      return mv;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarItem(
        @PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("editarItem");

        Item item = itemService.buscaItemPeloId(id);

        if(item != null) {
            mv.addObject("item", item);
        } else {
            throw new ErroDeNegocio("Item não encontrado com o ID: " + id);
        }

        return mv;
    }

    @PostMapping("/atualizar/{id}")
    public ModelAndView atualizarItem(
    @PathVariable("id") Long id, 
    @Valid @ModelAttribute("quantidadeEstoque") Integer quantidadeEstoque, 
    @Valid @ModelAttribute("nome") String nome, 
    BindingResult result) {

        ModelAndView erroAoEditar = new ModelAndView("editarItem");
        ModelAndView sucessoAoEditar = new ModelAndView("redirect:/item/editar/{id}?success");

        if (result.hasErrors()) {
            erroAoEditar.addObject("quantidadeEstoque", quantidadeEstoque);
            erroAoEditar.addObject("nome", nome);
            return erroAoEditar;
        }
        
        itemService.atualizaItem(id, quantidadeEstoque, nome);
        return sucessoAoEditar;
    }

    @GetMapping("/deletar/{id}")
    public ModelAndView deletarItem(@PathVariable("id") long id) {
        ModelAndView sucessoAoDeletar = new ModelAndView("redirect:/item/listar?success");
        itemService.deletaItem(id);
        return sucessoAoDeletar;
    }
}