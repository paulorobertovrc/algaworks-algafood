package br.dev.pauloroberto.algafood.api.exception.handler;

import lombok.Getter;

@Getter
public enum ProblemType {

        ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada"),
        ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
        ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
        PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
        ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
        MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
        RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
        ERRO_DE_VALIDACAO("/erro-de-validacao", "Erro de validação");

        private final String uri;
        private final String title;

        ProblemType(String path, String title) {
            this.uri = "https://algafood.com.br" + path;
            this.title = title;
        }

}
