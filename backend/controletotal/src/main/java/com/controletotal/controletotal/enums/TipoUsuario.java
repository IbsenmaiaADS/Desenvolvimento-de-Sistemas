package com.controletotal.controletotal.enums;

public enum TipoUsuario {
    ADMIN("admin"),
    ALMOXARIFE("almoxarife"),
    VISITANTE("visitante");

    private String tipo;

    TipoUsuario(String tipo) {
        this.tipo = tipo;
    }

    String getTipo() {
        return tipo;
    }
}
