package br.dev.pauloroberto.algafood.api.assembler;

import br.dev.pauloroberto.algafood.api.model.input.ProdutoInputDto;
import br.dev.pauloroberto.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoDomainObjectAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public Produto toDomainObject(ProdutoInputDto produtoInput) {
        return modelMapper.map(produtoInput, Produto.class);
    }

    public void copyToDomainObject(ProdutoInputDto produtoInput, Produto produto) {
        modelMapper.map(produtoInput, produto);
    }

}
