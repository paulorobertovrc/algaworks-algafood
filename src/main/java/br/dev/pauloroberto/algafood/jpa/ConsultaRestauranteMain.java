package br.dev.pauloroberto.algafood.jpa;

import br.dev.pauloroberto.algafood.AlgaworksAlgafoodApplication;
import br.dev.pauloroberto.algafood.domain.repository.RestauranteRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class ConsultaRestauranteMain {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgaworksAlgafoodApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);
        restauranteRepository.listar()
                .forEach(restaurante -> System.out.println(restaurante.getNome()));
    }
}
