package br.dev.pauloroberto.algafood.core.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Validated
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {

    @NotNull
    private String remetente;

    private Implementacao impl = Implementacao.FAKE;

    public enum Implementacao {
        FAKE, SMTP
    }

}
