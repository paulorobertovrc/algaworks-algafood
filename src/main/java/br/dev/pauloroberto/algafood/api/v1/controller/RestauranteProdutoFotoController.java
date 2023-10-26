package br.dev.pauloroberto.algafood.api.v1.controller;

import br.dev.pauloroberto.algafood.api.v1.assembler.FotoProdutoDtoAssembler;
import br.dev.pauloroberto.algafood.api.v1.model.FotoProdutoDto;
import br.dev.pauloroberto.algafood.api.v1.model.input.FotoProdutoInputDto;
import br.dev.pauloroberto.algafood.api.v1.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import br.dev.pauloroberto.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.dev.pauloroberto.algafood.domain.model.FotoProduto;
import br.dev.pauloroberto.algafood.domain.model.Produto;
import br.dev.pauloroberto.algafood.domain.service.CadastroProdutoService;
import br.dev.pauloroberto.algafood.domain.service.CatalogoFotoProdutoService;
import br.dev.pauloroberto.algafood.domain.service.FotoStorageService;
import br.dev.pauloroberto.algafood.domain.service.FotoStorageService.FotoRecuperada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/produtos/{produtoId}/foto",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoFotoController implements RestauranteProdutoFotoControllerOpenApi {
    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProdutoService;
    @Autowired
    private CadastroProdutoService cadastroProdutoService;
    @Autowired
    private FotoProdutoDtoAssembler fotoProdutoDtoAssembler;
    @Autowired
    private FotoStorageService fotoStorageService;

    @GetMapping
    public FotoProdutoDto buscar(@PathVariable Long produtoId, @PathVariable Long restauranteId) {
        FotoProduto fotoProduto = catalogoFotoProdutoService.verificarSeExiste(produtoId, restauranteId);

        return fotoProdutoDtoAssembler.toDto(fotoProduto);
    }


    @GetMapping(produces = MediaType.ALL_VALUE)
    public ResponseEntity<?> servir(@PathVariable Long produtoId,
                                    @PathVariable Long restauranteId,
                                    @RequestHeader(name = "accept") String acceptHeader)
            throws HttpMediaTypeNotAcceptableException {
        try {
            FotoProduto fotoProduto = catalogoFotoProdutoService.verificarSeExiste(produtoId, restauranteId);

            MediaType mediaType = MediaType.parseMediaType(fotoProduto.getContentType());
            List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);

            verificarCompatibilidadeMediaType(mediaType, mediaTypesAceitas);

            FotoRecuperada fotoRecuperada = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());

            if (fotoRecuperada.temUrl()) {
                return ResponseEntity.status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
                        .build();
            } else {
                return ResponseEntity.ok()
                        .contentType(mediaType)
                        .body(new InputStreamResource(fotoRecuperada.getInputStream()));
            }
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
    public FotoProdutoDto atualizar(@PathVariable Long produtoId,
                                    @PathVariable Long restauranteId,
                                    @Valid FotoProdutoInputDto fotoProdutoInputDto,
                                    @RequestPart() MultipartFile arquivo) throws IOException {
        Produto produto = cadastroProdutoService.verificarSeExiste(produtoId, restauranteId);

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

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long produtoId,
                        @PathVariable Long restauranteId) {
        catalogoFotoProdutoService.remover(produtoId, restauranteId);
    }

}
