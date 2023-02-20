package br.dev.pauloroberto.algafood.domain.exception;

public class EntidadeEmUsoException extends NegocioException {
    public EntidadeEmUsoException(String mensagem) {
        super(mensagem);
    }
}
