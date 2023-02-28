package br.dev.pauloroberto.algafood.api.assembler;

import br.dev.pauloroberto.algafood.api.model.CozinhaDto;
import br.dev.pauloroberto.algafood.api.model.input.CozinhaInputDto;
import br.dev.pauloroberto.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CozinhaDtoAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public CozinhaDto toDto(Cozinha cozinha) {
        return modelMapper.map(cozinha, CozinhaDto.class);
    }

    public List<CozinhaDto> toDtoList(List<Cozinha> cozinhas) {
        return cozinhas.stream()
                .map(this::toDto)
                .toList();
    }

    public CozinhaInputDto toInputDto(Cozinha cozinha) {
        return modelMapper.map(cozinha, CozinhaInputDto.class);
    }

}
