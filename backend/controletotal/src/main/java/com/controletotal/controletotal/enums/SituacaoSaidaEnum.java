package com.controletotal.controletotal.enums;

import lombok.Getter;

@Getter
public enum SituacaoSaidaEnum {
    AGUARDANDO_APROVACAO(0),
    APROVADA(1),
    REPROVADA(2);

    private final int codSituacao;

    SituacaoSaidaEnum(int codSituacao) {
        this.codSituacao = codSituacao;
    }
}
