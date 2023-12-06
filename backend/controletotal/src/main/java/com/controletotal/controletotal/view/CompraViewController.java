package com.controletotal.controletotal.view;

import com.controletotal.controletotal.dto.CompraDto;
import com.controletotal.controletotal.dto.pdf.PdfDto;
import com.controletotal.controletotal.service.CompraService;
import com.itextpdf.text.DocumentException;
import com.controletotal.controletotal.controller.ComprasController;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.lang.Long;
import java.time.LocalDate;

@RestController
@Validated
@RequestMapping("compra")
@RequiredArgsConstructor
@Tag(name = "compraView", description = "Gerenciar view de compras")
@CrossOrigin(origins = "*")
public class CompraViewController {
    private final CompraService compraService;
    private final ComprasController comprasController;


    @GetMapping("/cadastrar")
    public ModelAndView formularioCadastro(){
        ModelAndView mv = new ModelAndView("cadastrarCompra");
        CompraDto compra = new CompraDto();
        mv.addObject("compra", compra);
        return mv;
    }

    @PostMapping("/cadastrar")
    public ModelAndView cadastraCompra(@Valid @ModelAttribute("compra") CompraDto compraDto, BindingResult result){
        ModelAndView erroAoCadastrar = new ModelAndView("cadastrarCompra");
        ModelAndView sucessoAoCadastrar = new ModelAndView("redirect:/compra/cadastrar?success");

        if (result.hasErrors()) {
            erroAoCadastrar.addObject("compra", compraDto);
            return erroAoCadastrar;
        }

        ResponseEntity.status(HttpStatus.CREATED).body(compraService.cadastrarCompra(compraDto)).getBody();
        return sucessoAoCadastrar;
    }

    @GetMapping("/relatorio")
    public ModelAndView buscaRelatorio() {
        ModelAndView mv = new ModelAndView("relatorioCompras");
        return mv;
    }

    @PostMapping("/relatorio")
    public ModelAndView geraRelatorio(
    @Valid @ModelAttribute("dataInicial") LocalDate dataInicial,
    @Valid @ModelAttribute("dataFinal") LocalDate dataFinal,
    @Valid @ModelAttribute("idFornecedor") Long idFornecedor,
    BindingResult result) throws DocumentException{
    ModelAndView erroAoGerar = new ModelAndView("relatorioCompras");

        PdfDto pdf = compraService.gerarRelatorioPDF(dataInicial, dataFinal, idFornecedor);
        String situacao = idFornecedor == null ? "compras" : "compras_por_fornecedor";

        
        comprasController.prepareResponseDataWithHeaders(situacao, pdf);

        return erroAoGerar;

    }
}