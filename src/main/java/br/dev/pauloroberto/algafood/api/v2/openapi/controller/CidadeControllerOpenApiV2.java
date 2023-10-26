package br.dev.pauloroberto.algafood.api.v2.openapi.controller;

import br.dev.pauloroberto.algafood.api.exception.handler.Problem;
import br.dev.pauloroberto.algafood.api.v2.model.CidadeDtoV2;
import br.dev.pauloroberto.algafood.api.v2.model.input.CidadeInputDtoV2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApiV2 {
    @ApiOperation("Lista as cidades")
    CollectionModel<CidadeDtoV2> listar();

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "ID da cidade inválido", content = @Content(
                    schema = @Schema(implementation = Problem.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(
                    schema = @Schema(implementation = Problem.class), mediaType = "application/json"))
    })
    CidadeDtoV2 buscar(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long id);

    @ApiOperation("Cadastra uma cidade")
    @ApiResponse(responseCode = "201", description = "Cidade cadastrada", content = @Content(
            schema = @Schema(implementation = Problem.class), mediaType = "application/json"))
    CidadeDtoV2 adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true)
                          CidadeInputDtoV2 cidadeInput);

    @ApiOperation("Atualiza uma cidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cidade atualizada"),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(
                    schema = @Schema(implementation = Problem.class), mediaType = "application/json"))
    })
    CidadeDtoV2 atualizar(@ApiParam("ID de uma cidade")Long id,
                        @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados",
                                required = true) CidadeInputDtoV2 cidadeInput);

    @ApiOperation("Exclui uma cidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cidade excluída"),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(
                    schema = @Schema(implementation = Problem.class), mediaType = "application/json"))
    })
    void remover(@ApiParam(value = "ID de uma cidade", required = true) Long id);

}
