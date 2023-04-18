package br.dev.pauloroberto.algafood.api.openapi.controller;

import br.dev.pauloroberto.algafood.api.exception.handler.Problem;
import br.dev.pauloroberto.algafood.api.model.GrupoDto;
import br.dev.pauloroberto.algafood.api.model.input.GrupoInputDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {
    @ApiOperation("Lista os grupos")
    List<GrupoDto> listar();

    @ApiOperation("Busca um grupo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID do grupo inválido", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    GrupoDto buscar(@ApiParam(value = "ID de um Grupo", example = "1") Long id);

    @ApiOperation("Cadastra um grupo")
    @ApiResponse(responseCode = "201", description = "Grupo cadastrado")
    GrupoDto adicionar(@ApiParam(name = "corpo", value = "Representação de um novo grupo") GrupoInputDto grupoInput);

    @ApiOperation("Atualiza um grupo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grupo atualizado"),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    GrupoDto atualizar(@ApiParam(name = "ID de um Grupo", value = "1") Long id,
                       @ApiParam(name = "corpo", value = "Representação de um Grupo com os novos dados")
                       GrupoInputDto grupoInput);

    @ApiOperation("Remove um grupo")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Grupo removido"),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    void remover(@ApiParam(name = "ID de um Grupo", example = "1") Long id);

}
