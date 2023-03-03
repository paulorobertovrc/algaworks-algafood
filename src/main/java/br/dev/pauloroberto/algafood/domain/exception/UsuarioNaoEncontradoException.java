package br.dev.pauloroberto.algafood.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {
    public UsuarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public UsuarioNaoEncontradoException(Long id) {
        super(String.format("Não existe um cadastro de usuário com código %d", id));
    }

}
