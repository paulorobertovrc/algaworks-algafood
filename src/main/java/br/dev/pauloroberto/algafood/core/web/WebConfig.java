package br.dev.pauloroberto.algafood.core.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // Por padrão, a anotação @CrossOrigin permite requisições de qualquer origem
                .allowedMethods("*");  // Por padrão, a anotação @CrossOrigin permite requisições de qualquer método
//                .maxAge(30) // Habilita o cache para as requisições de CORS
    }

//    @Override
//    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) { // Habilita o suporte a JSON no Content Negotiation
//        configurer.defaultContentType(AlgaMediaTypes.V2_APPLICATION_JSON);
//    }

    @Bean
    public Filter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter(); // Habilita o suporte a shallow ETag
    }

}
