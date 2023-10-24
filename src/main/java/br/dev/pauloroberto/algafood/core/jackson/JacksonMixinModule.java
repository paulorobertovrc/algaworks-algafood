package br.dev.pauloroberto.algafood.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {
    public JacksonMixinModule() {
//        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
//        setMixInAnnotation(Cidade.class, CidadeMixin.class);
//        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
//        setMixInAnnotation(Produto.class, ProdutoMixin.class);
    }
}
