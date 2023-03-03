package br.dev.pauloroberto.algafood.domain.exception;

public class SenhaUsuarioNaoCoincideException extends NegocioException {
    public SenhaUsuarioNaoCoincideException() {
        super("Senha atual não coincide com a senha do usuário");
    }

}
