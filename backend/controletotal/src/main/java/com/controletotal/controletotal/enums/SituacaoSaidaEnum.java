package com.controletotal.controletotal.enums;

import com.controletotal.controletotal.handler.ErroDeNegocio;
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

    public static int getByCodSituacao(int codSituacao) {
        for (SituacaoSaidaEnum codigo : SituacaoSaidaEnum.values()) {
            if (codigo.codSituacao == codSituacao) {
                return codigo.getCodSituacao();
            }
        }
        throw new ErroDeNegocio("Código de solicitação inválido.");
    }
}
