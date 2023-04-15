package br.dev.pauloroberto.algafood.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {
    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.dev.pauloroberto.algafood.api")) // Exibe apenas os endpoints de um pacote específico
//                .paths(PathSelectors.ant("/restaurantes/*")) // Este filtro exibe apenas os endpoints que começam com /restaurantes/
//                .paths(PathSelectors.ant("/restaurantes/**")) // Este filtro exibe apenas os endpoints que começam com /restaurantes/ e terminam com qualquer coisa
                .paths(PathSelectors.any()) // Este filtro exibe todos os endpoints
                .build();
    }

}
