package br.dev.pauloroberto.algafood.core.email;

import br.dev.pauloroberto.algafood.domain.service.EnvioEmailService;
import br.dev.pauloroberto.algafood.infrastructure.service.email.FakeEnvioEmailService;
import br.dev.pauloroberto.algafood.infrastructure.service.email.SandboxEnvioEmailService;
import br.dev.pauloroberto.algafood.infrastructure.service.email.SmtpEnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {
    @Autowired
    private EmailProperties emailProperties;

    @Bean
    public EnvioEmailService envioEmailService() {
        return switch (emailProperties.getImpl()) {
            case FAKE -> new FakeEnvioEmailService();
            case SMTP -> new SmtpEnvioEmailService();
            case SANDBOX -> new SandboxEnvioEmailService();
        };
    }
}
