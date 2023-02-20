package br.dev.pauloroberto.algafood.domain.exception;

public abstract class EntidadeNaoEncontradaException extends NegocioException {
    public EntidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
