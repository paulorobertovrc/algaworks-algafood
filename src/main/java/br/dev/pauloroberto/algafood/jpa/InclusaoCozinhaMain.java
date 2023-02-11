package br.dev.pauloroberto.algafood.jpa;

import br.dev.pauloroberto.algafood.AlgaworksAlgafoodApplication;
import br.dev.pauloroberto.algafood.domain.model.Cozinha;
import br.dev.pauloroberto.algafood.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class InclusaoCozinhaMain {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgaworksAlgafoodApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);

        Cozinha cozinha1 = new Cozinha();
        cozinha1.setNome("Portuguesa");
        Cozinha cozinha2 = new Cozinha();
        cozinha2.setNome("Japonesa");

        cozinhaRepository.salvar(cozinha1);
        cozinhaRepository.salvar(cozinha2);

        cozinhaRepository.listar()
                .forEach(System.out::println);
    }
}
