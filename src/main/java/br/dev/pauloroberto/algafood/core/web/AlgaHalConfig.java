package br.dev.pauloroberto.algafood.core.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.mediatype.hal.HalConfiguration;
import org.springframework.http.MediaType;

@Configuration
public class AlgaHalConfig {

    @Bean
    public HalConfiguration globalPolicy() { // Habilita o suporte a HAL no Content Negotiation
        return new HalConfiguration()
                .withMediaType(MediaType.APPLICATION_JSON);
//                .withMediaType(AlgaMediaTypes.V1_APPLICATION_JSON) // Habilita o suporte a JSON no Content Negotiation
//                .withMediaType(AlgaMediaTypes.V2_APPLICATION_JSON);
    }

}