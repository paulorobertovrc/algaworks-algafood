package br.dev.pauloroberto.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {
    public PedidoNaoEncontradoException(String codigo) {
        super(String.format("Não existe um cadastro de pedido com código %s", codigo));
    }

}
