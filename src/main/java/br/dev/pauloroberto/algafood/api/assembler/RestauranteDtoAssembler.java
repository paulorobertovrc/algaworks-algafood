package br.dev.pauloroberto.algafood.api.assembler;

import br.dev.pauloroberto.algafood.api.model.RestauranteDto;
import br.dev.pauloroberto.algafood.api.model.input.RestauranteInputDto;
import br.dev.pauloroberto.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestauranteDtoAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public RestauranteDto toDto(Restaurante restaurante) {
        return modelMapper.map(restaurante, RestauranteDto.class);
    }

    public List<RestauranteDto> toDtoList(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(this::toDto)
                .toList();
    }

    public RestauranteInputDto toInputDto(Restaurante restaurante) {
        return modelMapper.map(restaurante, RestauranteInputDto.class);
    }

}
