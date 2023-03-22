package br.dev.pauloroberto.algafood.api.controller;

import br.dev.pauloroberto.algafood.api.assembler.FotoProdutoDtoAssembler;
import br.dev.pauloroberto.algafood.api.model.FotoProdutoDto;
import br.dev.pauloroberto.algafood.api.model.input.FotoProdutoInputDto;
import br.dev.pauloroberto.algafood.domain.model.FotoProduto;
import br.dev.pauloroberto.algafood.domain.model.Produto;
import br.dev.pauloroberto.algafood.domain.service.CadastroProdutoService;
import br.dev.pauloroberto.algafood.domain.service.CatalogoFotoProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {
    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProdutoService;
    @Autowired
    private CadastroProdutoService cadastroProdutoService;
    @Autowired
    private FotoProdutoDtoAssembler fotoProdutoDtoAssembler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoDto buscar(@PathVariable Long produtoId,
                                 @PathVariable Long restauranteId) {
        FotoProduto fotoProduto = catalogoFotoProdutoService.verificarSeExiste(produtoId, restauranteId);

        return fotoProdutoDtoAssembler.toDto(fotoProduto);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoDto atualizarFoto(@PathVariable Long produtoId,
                                        @PathVariable Long restauranteId,
                                        @Valid FotoProdutoInputDto fotoProdutoInputDto) throws IOException {
        Produto produto = cadastroProdutoService.verificarSeExiste(produtoId, restauranteId);
        MultipartFile arquivo = fotoProdutoInputDto.getArquivo();

        FotoProduto foto = new FotoProduto();
        foto.setProduto(produto);
        foto.setDescricao(fotoProdutoInputDto.getDescricao());
        foto.setNomeArquivo(arquivo.getOriginalFilename());
        foto.setContentType(arquivo.getContentType());
        foto.setTamanho(arquivo.getSize());

        return fotoProdutoDtoAssembler.toDto(
                catalogoFotoProdutoService.salvarFotoProduto(foto, arquivo.getInputStream())
        );
    }

}
