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
    @Bean
    public FilterRegistrationBean<SquigglyRequestFilter> squigglyRequestFilter(ObjectMapper objectMapper) {
        Squiggly.init(objectMapper, new RequestSquigglyContextProvider("campos", null));

        FilterRegistrationBean<SquigglyRequestFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new SquigglyRequestFilter());
        filterRegistrationBean.addUrlPatterns("/*"); // TODO: limitar para apenas as URLs que precisam
        filterRegistrationBean.setOrder(1);

        return filterRegistrationBean;
    }

}
