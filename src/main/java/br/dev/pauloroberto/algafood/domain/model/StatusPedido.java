package br.dev.pauloroberto.algafood.domain.model;

import lombok.Getter;

import java.util.List;

@Getter
public enum StatusPedido {
    CRIADO("Criado"),
    CONFIRMADO("Confirmado", CRIADO),
    ENTREGUE("Entregue", CONFIRMADO),
    CANCELADO("Cancelado", CRIADO);

    private final String descricao;
    private final List<StatusPedido> statusAnteriores;

    StatusPedido(String descricao, StatusPedido... statusAnteriores) {
        this.descricao = descricao;
        this.statusAnteriores = List.of(statusAnteriores);
    }

    public boolean naoPodeSerAlteradoPara(StatusPedido novoStatus) {
        return !novoStatus.statusAnteriores.contains(this);
    }

    public boolean podeSerAlteradoPara(StatusPedido statusPedido) {
        return !naoPodeSerAlteradoPara(statusPedido);
    }

}
