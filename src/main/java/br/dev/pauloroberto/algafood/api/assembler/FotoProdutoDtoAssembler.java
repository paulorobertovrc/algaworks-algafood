package br.dev.pauloroberto.algafood.api.assembler;

import br.dev.pauloroberto.algafood.api.model.FotoProdutoDto;
import br.dev.pauloroberto.algafood.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoDtoAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public FotoProdutoDto toDto(FotoProduto fotoProduto) {
        return modelMapper.map(fotoProduto, FotoProdutoDto.class);
    }

}
