package br.dev.pauloroberto.algafood.core.squiggly;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.web.RequestSquigglyContextProvider;
import com.github.bohnman.squiggly.web.SquigglyRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SquigglyConfig {
    // Exemplo: http://localhost:8080/restaurantes?campos=nome,taxaFrete - Neste exemplo, apenas as propriedades nome e taxaFrete serão retornadas na resposta.
    // O Squiggly permite filtrar as propriedades que serão retornadas na resposta de uma requisição.
    @Bean
    public FilterRegistrationBean<SquigglyRequestFilter> squigglyRequestFilter(ObjectMapper objectMapper) {
        Squiggly.init(objectMapper, new RequestSquigglyContextProvider("campos", null));

        FilterRegistrationBean<SquigglyRequestFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new SquigglyRequestFilter());
        filterRegistrationBean.addUrlPatterns("/pedidos/*", "/restaurantes/*");
        filterRegistrationBean.setOrder(1);

        return filterRegistrationBean;
    }

}
