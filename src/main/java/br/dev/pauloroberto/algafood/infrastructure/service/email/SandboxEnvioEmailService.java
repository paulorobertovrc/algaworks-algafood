package br.dev.pauloroberto.algafood.infrastructure.service.email;

import br.dev.pauloroberto.algafood.core.email.EmailProperties;
import org.springframework.beans.factory.annotation.Autowired;

public class SandboxEnvioEmailService extends SmtpEnvioEmailService {
    @Autowired
    private EmailProperties emailProperties;

    @Override
    public void enviar(Mensagem mensagem) {
        super.enviar(
                Mensagem.builder()
                .assunto(mensagem.getAssunto())
                .corpo(mensagem.getCorpo())
                .destinatario(emailProperties.getSandbox().getDestinatario())
                .variaveis(mensagem.getVariaveis())
                .build()
        );
    }

}
