package com.controletotal.controletotal.handler;

public class ErroDeNegocio extends RuntimeException {
    public ErroDeNegocio(String mensagem) {
        super(mensagem);
    }
}

