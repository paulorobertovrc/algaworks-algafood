package br.dev.pauloroberto.algafood.api.assembler;

import br.dev.pauloroberto.algafood.api.model.GrupoDto;
import br.dev.pauloroberto.algafood.api.model.input.GrupoInputDto;
import br.dev.pauloroberto.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GrupoDtoAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public GrupoDto toDto(Grupo grupo) {
        return modelMapper.map(grupo, GrupoDto.class);
    }

    public List<GrupoDto> toDtoList(List<Grupo> grupos) {
        return grupos.stream()
                .map(this::toDto)
                .toList();
    }

    public GrupoInputDto toInputDto(Grupo grupo) {
        return modelMapper.map(grupo, GrupoInputDto.class);
    }
}
