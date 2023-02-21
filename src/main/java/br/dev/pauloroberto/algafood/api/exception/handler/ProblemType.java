package br.dev.pauloroberto.algafood.api.exception.handler;

import lombok.Getter;

@Getter
public enum ProblemType {

        RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
        ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
        ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
        PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
        ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
        MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
        ERRO_DE_VALIDACAO("/erro-de-validacao", "Erro de validação"),
        DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos");

        private final String uri;
        private final String title;

        ProblemType(String path, String title) {
            this.uri = "https://algafood.com.br" + path;
            this.title = title;
        }

}
