package br.dev.pauloroberto.algafood.api.controller;

import br.dev.pauloroberto.algafood.api.assembler.FotoProdutoDtoAssembler;
import br.dev.pauloroberto.algafood.api.model.FotoProdutoDto;
import br.dev.pauloroberto.algafood.api.model.input.FotoProdutoInputDto;
import br.dev.pauloroberto.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.dev.pauloroberto.algafood.domain.model.FotoProduto;
import br.dev.pauloroberto.algafood.domain.model.Produto;
import br.dev.pauloroberto.algafood.domain.service.CadastroProdutoService;
import br.dev.pauloroberto.algafood.domain.service.CatalogoFotoProdutoService;
import br.dev.pauloroberto.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {
    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProdutoService;
    @Autowired
    private CadastroProdutoService cadastroProdutoService;
    @Autowired
    private FotoProdutoDtoAssembler fotoProdutoDtoAssembler;
    @Autowired
    private FotoStorageService fotoStorageService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoDto buscar(@PathVariable Long produtoId,
                                 @PathVariable Long restauranteId) {
        FotoProduto fotoProduto = catalogoFotoProdutoService.verificarSeExiste(produtoId, restauranteId);

        return fotoProdutoDtoAssembler.toDto(fotoProduto);
    }


    @GetMapping
    public ResponseEntity<InputStreamResource> servirFoto(@PathVariable Long produtoId,
                                                          @PathVariable Long restauranteId,
                                                          @RequestHeader(name = "accept") String acceptHeader)
            throws HttpMediaTypeNotAcceptableException {
        try {
            FotoProduto fotoProduto = catalogoFotoProdutoService.verificarSeExiste(produtoId, restauranteId);

            MediaType mediaType = MediaType.parseMediaType(fotoProduto.getContentType());
            List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);

            verificarCompatibilidadeMediaType(mediaType, mediaTypesAceitas);

            InputStream inputStream = fotoStorageService.recuperar(fotoProduto.getNomeArquivo()).getInputStream();

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(new InputStreamResource(inputStream));
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private void verificarCompatibilidadeMediaType(MediaType mediaType, List<MediaType> mediaTypesAceitas)
            throws HttpMediaTypeNotAcceptableException {
        boolean compativel = mediaTypesAceitas.stream()
                .anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaType));

        if (!compativel) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
        }
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
