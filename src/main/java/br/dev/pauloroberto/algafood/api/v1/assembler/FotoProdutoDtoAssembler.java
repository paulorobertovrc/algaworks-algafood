package br.dev.pauloroberto.algafood.api.v1.assembler;

import br.dev.pauloroberto.algafood.api.v1.controller.RestauranteProdutoFotoController;
import br.dev.pauloroberto.algafood.api.v1.helper.LinkHelper;
import br.dev.pauloroberto.algafood.api.v1.model.FotoProdutoDto;
import br.dev.pauloroberto.algafood.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoDtoAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoDto> {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LinkHelper linkHelper;

    public FotoProdutoDtoAssembler() {
        super(RestauranteProdutoFotoController.class, FotoProdutoDto.class);
    }

    public FotoProdutoDto toDto(FotoProduto fotoProduto) {
        return modelMapper.map(fotoProduto, FotoProdutoDto.class);
    }

    @Override
    public FotoProdutoDto toModel(FotoProduto foto) {
        FotoProdutoDto fotoProdutoDto = modelMapper.map(foto, FotoProdutoDto.class);
        fotoProdutoDto.add(linkHelper.linkToFotoProduto(foto.getRestauranteId(), foto.getProduto().getId()));
        fotoProdutoDto.add(linkHelper.linkToProduto(foto.getRestauranteId(), foto.getProduto().getId(), "produto"));

        return fotoProdutoDto;
    }
    
}
