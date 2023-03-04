package br.dev.pauloroberto.algafood.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public ProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
    public ProdutoNaoEncontradoException(Long produtoId, Long restauranteId) {
        super(String.format("Não existe um cadastro de produto com código %d no restaurante de código %d",
                produtoId, restauranteId));
    }

}
