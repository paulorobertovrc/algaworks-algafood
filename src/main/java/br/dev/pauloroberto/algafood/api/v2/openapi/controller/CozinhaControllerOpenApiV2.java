package br.dev.pauloroberto.algafood.api.v2.openapi.controller;

import br.dev.pauloroberto.algafood.api.exception.handler.Problem;
import br.dev.pauloroberto.algafood.api.v2.model.CozinhaDtoV2;
import br.dev.pauloroberto.algafood.api.v2.model.input.CozinhaInputDtoV2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApiV2 {
    @ApiOperation("Lista as cozinhas")
    PagedModel<CozinhaDtoV2> listar(Pageable pageable);

    @ApiOperation("Busca uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID da cozinha inválido", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            )),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            ))
    })
    CozinhaDtoV2 buscar(@ApiParam(value = "ID de uma cozinha", example = "1", required = true) Long id);

    @ApiOperation("Cadastra uma cozinha")
    @ApiResponse(responseCode = "201", description = "Cozinha cadastrada", content = @Content(
            schema = @Schema(implementation = Problem.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    CozinhaDtoV2 adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true)
                           CozinhaInputDtoV2 cozinhaInput);

    @ApiOperation("Atualiza uma cozinha")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cozinha atualizada", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            )),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            ))
    })
    CozinhaDtoV2 atualizar(@ApiParam(value = "ID de uma cidade", required = true) Long id,
                         @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados",
                                 required = true) CozinhaInputDtoV2 cozinhaInput);

    @ApiOperation("Remove uma cozinha")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cozinha removida", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            )),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            ))
    })
    void remover(@ApiParam(value = "ID de uma cidade", required = true) Long id);

}
