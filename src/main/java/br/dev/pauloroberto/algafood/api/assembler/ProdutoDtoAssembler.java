package br.dev.pauloroberto.algafood.api.assembler;

import br.dev.pauloroberto.algafood.api.controller.RestauranteProdutoController;
import br.dev.pauloroberto.algafood.api.helper.LinkHelper;
import br.dev.pauloroberto.algafood.api.model.ProdutoDto;
import br.dev.pauloroberto.algafood.api.model.input.ProdutoInputDto;
import br.dev.pauloroberto.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProdutoDtoAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoDto> {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LinkHelper linkHelper;

    public ProdutoDtoAssembler() {
        super(RestauranteProdutoController.class, ProdutoDto.class);
    }

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

    @Override
    public ProdutoDto toModel(Produto produto) {
        ProdutoDto produtoDto = createModelWithId(produto.getId(), produto, produto.getRestaurante().getId());

        modelMapper.map(produto, produtoDto);
        produtoDto.add(linkHelper.linkToProdutos(produto.getRestaurante().getId(), "produtos"));
        produtoDto.add(linkHelper.linkToFotoProduto(produto.getRestaurante().getId(), produto.getId(), "foto"));

        return produtoDto;
    }

}
