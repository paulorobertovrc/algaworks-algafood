package br.dev.pauloroberto.algafood.core.modelmapper;

import br.dev.pauloroberto.algafood.api.v1.model.input.ItemPedidoInputDto;
import br.dev.pauloroberto.algafood.api.v2.model.input.CidadeInputDtoV2;
import br.dev.pauloroberto.algafood.domain.model.Cidade;
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

        modelMapper.createTypeMap(CidadeInputDtoV2.class, Cidade.class)
                .addMappings(mapper -> mapper.skip(Cidade::setId));

        return modelMapper;
    }

}
