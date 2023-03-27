package br.dev.pauloroberto.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

import java.util.Set;

public interface EnvioEmailService {
    void enviar(Mensagem mensagem);
    void enviar(Mensagem mensagem, String remetente);

    @Getter
    @Builder
    class Mensagem {
        @Singular
        private Set<String> destinatarios;
        @NonNull
        private String assunto;
        @NonNull
        private String corpo;
    }

}
