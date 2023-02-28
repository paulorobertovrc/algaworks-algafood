package br.dev.pauloroberto.algafood.core.jackson;

import br.dev.pauloroberto.algafood.domain.model.Cidade;
import br.dev.pauloroberto.algafood.domain.model.Cozinha;
import br.dev.pauloroberto.algafood.domain.model.Produto;
import br.dev.pauloroberto.algafood.domain.model.Restaurante;
import br.dev.pauloroberto.algafood.api.model.mixin.CidadeMixin;
import br.dev.pauloroberto.algafood.api.model.mixin.CozinhaMixin;
import br.dev.pauloroberto.algafood.api.model.mixin.ProdutoMixin;
import br.dev.pauloroberto.algafood.api.model.mixin.RestauranteMixin;
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
