package br.dev.pauloroberto.algafood.api.v1.openapi.controller;

import br.dev.pauloroberto.algafood.api.exception.handler.Problem;
import br.dev.pauloroberto.algafood.api.v1.model.PermissaoDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Api(tags = "Grupos")
public interface GrupoPermissaoControllerOpenApi {
    @ApiOperation("Lista as permissões associadas a um grupo")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID do grupo inválido", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(
                    schema = @Schema(implementation = Problem.class)))
    })
    CollectionModel<PermissaoDto> listar(@ApiParam(value = "ID do grupo", example = "1", required = true) Long grupoId);

    @ApiOperation("Associação de permissão com grupo")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Associação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Grupo ou permissão não encontrado", content = @Content(
                    schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<Void> associar(@ApiParam(value = "ID do grupo", example = "1", required = true) Long grupoId,
                            @ApiParam(value = "ID da permissão", example = "1", required = true) Long permissaoId);

    @ApiOperation("Desassociação de permissão com grupo")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Desassociação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Grupo ou permissão não encontrado", content = @Content(
                    schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<Void> desassociar(@ApiParam(value = "ID do grupo", example = "1", required = true) Long grupoId,
                     @ApiParam(value = "ID da pernissão", example = "1", required = true) Long permissaoId);

}
