package com.controletotal.controletotal.handler;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BindException.class, ValidationException.class, ErroDeNegocio.class, ConstraintViolationException.class})
    public ResponseEntity<List<ErroDeValidacao>> manipularErroDeValidacao(Exception ex) {
        List<ErroDeValidacao> erros;

        if (ex instanceof BindException) {
            erros = ((BindException) ex).getBindingResult().getFieldErrors()
                    .stream()
                    .map(fieldError -> new ErroDeValidacao(fieldError.getDefaultMessage()))
                    .collect(Collectors.toList());
        } else if (ex instanceof ErroDeNegocio) {
            erros = List.of(new ErroDeValidacao(ex.getMessage()));
        } else if (ex instanceof ConstraintViolationException) {
            erros = List.of(new ErroDeValidacao(formataMensagem(ex.getMessage())));
        } else if (ex instanceof ValidationException) {
            erros = List.of(new ErroDeValidacao(ex.getMessage()));
        } else {
            erros = List.of(new ErroDeValidacao("Erro de validação"));
        }

        return ResponseEntity.badRequest().body(erros);
    }

    private String formataMensagem(String message) {
        return message.replaceFirst(".*:\\s*", "");
    }
}
