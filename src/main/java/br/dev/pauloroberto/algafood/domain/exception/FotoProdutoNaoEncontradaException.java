package br.dev.pauloroberto.algafood.domain.exception;

public class FotoProdutoNaoEncontradaException extends EntidadeNaoEncontradaException {
    public FotoProdutoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public FotoProdutoNaoEncontradaException(Long produtoId, Long restauranteId) {
        super(String.format("Não existe um cadastro de foto do produto com código %d para o restaurante de código %d",
                produtoId,
                restauranteId));
    }

}
