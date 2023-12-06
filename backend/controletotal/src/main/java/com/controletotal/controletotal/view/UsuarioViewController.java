package com.controletotal.controletotal.view;

import com.controletotal.controletotal.dto.UsuarioDto;
import com.controletotal.controletotal.entity.Usuario;
import com.controletotal.controletotal.enums.TipoUsuario;
import com.controletotal.controletotal.handler.ErroDeNegocio;
import com.controletotal.controletotal.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.ContentTypeUtils;

import java.lang.Long;
import java.util.List;

@RestController
@Validated
@RequestMapping("usuario")
@RequiredArgsConstructor
@Tag(name = "usuarioView", description = "Gerenciar view de usuário")
@CrossOrigin(origins = "*")
public class UsuarioViewController {
    private final UsuarioService usuarioService;


    @GetMapping("/cadastrar")
    public ModelAndView formularioCadastro(){
        ModelAndView mv = new ModelAndView("cadastrarUsuario");
        UsuarioDto usuario = new UsuarioDto();
        mv.addObject("usuario", usuario);
        return mv;
    }

    @PostMapping("/cadastrar")
    public ModelAndView cadastraUsuario(@Valid @ModelAttribute("usuario") UsuarioDto usuarioDto, BindingResult result){
        ModelAndView erroAoCadastrar = new ModelAndView("cadastrarUsuario");
        ModelAndView sucessoAoCadastrar = new ModelAndView("redirect:/usuario/cadastrar?success");

        if (usuarioService.buscaUsuarioPorEmail(usuarioDto.getEmail()) != null) {
            result.rejectValue("email", null, "Já existe um usuário registrado com esse email.");
        }

        if (result.hasErrors()) {
            erroAoCadastrar.addObject("usuario", usuarioDto);
            return erroAoCadastrar;
        }

        ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastraUsuario(usuarioDto)).getBody();
        return sucessoAoCadastrar;
    }

    @GetMapping("/listar")
    public ModelAndView listaUsuario() {
    ModelAndView mv = new ModelAndView("usuarios");
    List<Usuario> usuarios = usuarioService.buscaTodosOsUsuarios();
      mv.addObject("usuarios", usuarios);
      return mv;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarUsuario(
        @PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("editarUsuario");

        Usuario usuario = usuarioService.buscaUsuarioPorId(id);

        if(usuario != null) {
            mv.addObject("usuario", usuario);
        } else {
            throw new ErroDeNegocio("Usuário não encontrado com o ID: " + id);
        }

        return mv;
    }

    @PostMapping("/atualizar/{id}")
    public ModelAndView atualizarUsuario(
    @PathVariable("id") Long id, 
    @Valid @ModelAttribute("usuario") UsuarioDto usuarioDto, 
    BindingResult result) {

        ModelAndView erroAoEditar = new ModelAndView("editarUsuario");
        ModelAndView sucessoAoEditar = new ModelAndView("redirect:/usuario/editar/{id}?success");

        if (result.hasErrors()) {
            erroAoEditar.addObject("usuario", usuarioDto);
            return erroAoEditar;
        }
        usuarioService.atualizaUsuario(id, usuarioDto);

        return sucessoAoEditar;
    }

    @GetMapping("/deletar/{id}")
    public ModelAndView deletarUsuario(@PathVariable("id") Long id) {
        ModelAndView sucessoAoDeletar = new ModelAndView("redirect:/usuario/listar?success");
        usuarioService.deletaUsuario(id);
        return sucessoAoDeletar;
    }
}