package br.dev.pauloroberto.algafood.api.openapi.controller;

import br.dev.pauloroberto.algafood.api.exception.handler.Problem;
import br.dev.pauloroberto.algafood.api.model.EstadoDto;
import br.dev.pauloroberto.algafood.api.model.input.EstadoInputDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {
    @ApiOperation("Lista os estados")
    CollectionModel<EstadoDto> listar();

    @ApiOperation("Busca um estado por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID do estado inválido", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            )),
            @ApiResponse(responseCode = "404", description = "Estado não encontrado", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            ))
    })
    EstadoDto buscar(@ApiParam(value = "ID de um Estado", example = "1", required = true) Long id);

    @ApiOperation("Cadastra um estado")
    @ApiResponse(responseCode = "201", description = "Estado cadastrado")
    EstadoDto adicionar(@ApiParam(name = "corpo", value = "Representação de um novo estado", required = true)
                        EstadoInputDto estadoInput);

    @ApiOperation("Exclui um estado por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Estado excluído"),
            @ApiResponse(responseCode = "400", description = "ID do estado inválido", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            )),
            @ApiResponse(responseCode = "404", description = "Estado não encontrado", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            ))
    })
    void remover(@ApiParam(value = "ID de um Estado", example = "1", required = true) Long id);
}
