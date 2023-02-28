package br.dev.pauloroberto.algafood.api.assembler;

import br.dev.pauloroberto.algafood.api.model.CidadeDto;
import br.dev.pauloroberto.algafood.api.model.input.CidadeInputDto;
import br.dev.pauloroberto.algafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CidadeDtoAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public CidadeDto toDto(Cidade cidade) {
        return modelMapper.map(cidade, CidadeDto.class);
    }

    public List<CidadeDto> toDtoList(List<Cidade> cidades) {
        return cidades.stream()
                .map(this::toDto)
                .toList();
    }

    public CidadeInputDto toInputDto(Cidade cidade) {
        return modelMapper.map(cidade, CidadeInputDto.class);
    }

}
