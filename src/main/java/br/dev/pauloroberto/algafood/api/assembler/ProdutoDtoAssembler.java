package br.dev.pauloroberto.algafood.api.assembler;

import br.dev.pauloroberto.algafood.api.model.ProdutoDto;
import br.dev.pauloroberto.algafood.api.model.input.ProdutoInputDto;
import br.dev.pauloroberto.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProdutoDtoAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public ProdutoDto toDto(Produto produto) {
        return modelMapper.map(produto, ProdutoDto.class);
    }

    public List<ProdutoDto> toDtoList(List<Produto> produtos) {
        return produtos.stream()
                .map(this::toDto)
                .toList();
    }

    public ProdutoInputDto toInputDto(Produto produto) {
        return modelMapper.map(produto, ProdutoInputDto.class);
    }
}
