package br.dev.pauloroberto.algafood.api.controller.openapi;

import br.dev.pauloroberto.algafood.api.exception.handler.Problem;
import br.dev.pauloroberto.algafood.api.model.CidadeDto;
import br.dev.pauloroberto.algafood.api.model.input.CidadeInputDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {
    @ApiOperation("Lista as cidades")
    List<CidadeDto> listar();

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "ID da cidade inválido", content = @Content(
                    schema = @Schema(implementation = Problem.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(
                    schema = @Schema(implementation = Problem.class), mediaType = "application/json"))
    })
    CidadeDto buscar(@ApiParam(value = "ID de uma cidade", example = "1") Long id);

    @ApiOperation("Cadastra uma cidade")
    @ApiResponse(responseCode = "201", description = "Cidade cadastrada", content = @Content(
            schema = @Schema(implementation = Problem.class), mediaType = "application/json"))
    CidadeDto adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade")
                        CidadeInputDto cidadeInput);

    @ApiOperation("Atualiza uma cidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cidade atualizada"),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(
                    schema = @Schema(implementation = Problem.class), mediaType = "application/json"))
    })
    CidadeDto atualizar(@ApiParam("ID de uma cidade")Long id,
                        @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados")
                               CidadeInputDto cidadeInput);

    @ApiOperation("Exclui uma cidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cidade excluída"),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(
                    schema = @Schema(implementation = Problem.class), mediaType = "application/json"))
    })
    void remover(@ApiParam("ID de uma cidade") Long id);

}
