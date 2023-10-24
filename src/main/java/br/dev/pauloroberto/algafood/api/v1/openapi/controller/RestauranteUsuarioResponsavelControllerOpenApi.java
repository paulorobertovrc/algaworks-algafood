package br.dev.pauloroberto.algafood.api.v1.openapi.controller;

import br.dev.pauloroberto.algafood.api.exception.handler.Problem;
import br.dev.pauloroberto.algafood.api.v1.model.UsuarioDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Api(tags = "Restaurantes")
public interface RestauranteUsuarioResponsavelControllerOpenApi {
    @ApiModelProperty("Lista os usuários responsáveis associados a um restaurante")
    @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = Problem.class)
    ))
    CollectionModel<UsuarioDto> listar(@ApiParam(value = "ID do restaurante", example = "1", required = true)
                                       Long restauranteId);

    @ApiModelProperty("Associa um usuário responsável a um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Associação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante ou usuário não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Problem.class)
            ))
    })
    ResponseEntity<Void> associar(@ApiParam(value = "ID do restaurante", example = "1", required = true)
                                  Long restauranteId,
                            @ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId);

    @ApiModelProperty("Desassocia um usuário responsável de um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Desassociação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante ou usuário não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Problem.class)
                    ))
    })
    ResponseEntity<Void> desassociar(@ApiParam(value = "ID do restaurante", example = "1", required = true)
                                     Long restauranteId,
                     @ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId);

}
