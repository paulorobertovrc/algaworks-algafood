package br.dev.pauloroberto.algafood.core.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // Por padrão, a anotação @CrossOrigin permite requisições de qualquer origem
                .allowedMethods("*"); // Por padrão, a anotação @CrossOrigin permite requisições de qualquer método
    }

}
