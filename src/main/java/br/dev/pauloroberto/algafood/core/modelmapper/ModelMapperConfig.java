package br.dev.pauloroberto.algafood.core.modelmapper;

import br.dev.pauloroberto.algafood.api.model.input.ItemPedidoInputDto;
import br.dev.pauloroberto.algafood.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(ItemPedidoInputDto.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));

        return modelMapper;
    }

}
