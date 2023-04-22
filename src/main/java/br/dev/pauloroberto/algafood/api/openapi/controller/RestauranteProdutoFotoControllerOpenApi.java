package br.dev.pauloroberto.algafood.api.openapi.controller;

import br.dev.pauloroberto.algafood.api.exception.handler.Problem;
import br.dev.pauloroberto.algafood.api.model.FotoProdutoDto;
import br.dev.pauloroberto.algafood.api.model.input.FotoProdutoInputDto;
import br.dev.pauloroberto.algafood.domain.model.Produto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = "Produtos")
public interface RestauranteProdutoFotoControllerOpenApi {

    @ApiOperation(value = "Busca a foto do produto de um restaurante",
            produces = "application/json, image/jpeg, image/png")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID do restaurante ou produto inválido",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            )),
            @ApiResponse(responseCode = "404", description = "Foto não encontrada",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            ))
    })
    FotoProdutoDto buscar(@ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId,
                          @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId);

    @ApiOperation(value = "Busca a foto do produto de um restaurante", hidden = true)
    ResponseEntity<?> servir(Long produtoId, Long restauranteId, String acceptHeader)
            throws HttpMediaTypeNotAcceptableException;

    @ApiOperation("Atualiza a foto do produto de um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Foto do produto atualizada"),
            @ApiResponse(responseCode = "404", description = "Produto de restaurante não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Problem.class)))
    })
    FotoProdutoDto atualizar(@ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId,
                             @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
                             FotoProdutoInputDto fotoProdutoInputDto,
                             @ApiParam(value = "Arquivo da foto do produto (máximo 500KB, apenas JPG e PNG)",
                                     required = true) MultipartFile arquivo) throws IOException;

    @ApiOperation("Exclui a foto do produto de um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Foto do produto excluída"),
            @ApiResponse(responseCode = "400", description = "ID do restaurante ou produto inválido",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Produto.class)
                    )),
            @ApiResponse(responseCode = "404", description = "Foto não encontrada",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Problem.class)))
    })
    void remover(@ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId,
                 @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId);

}
