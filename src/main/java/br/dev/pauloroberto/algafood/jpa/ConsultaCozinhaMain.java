package br.dev.pauloroberto.algafood.jpa;

import br.dev.pauloroberto.algafood.AlgaworksAlgafoodApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class ConsultaCozinhaMain {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgaworksAlgafoodApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CadastroCozinha cadastroCozinha = applicationContext.getBean(CadastroCozinha.class);
        cadastroCozinha.listar()
                .forEach(cozinha -> System.out.println(cozinha.getNome()));
    }
}
