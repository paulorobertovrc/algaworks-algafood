package br.dev.pauloroberto.algafood.api.v1.openapi.controller;

import br.dev.pauloroberto.algafood.api.exception.handler.Problem;
import br.dev.pauloroberto.algafood.api.v1.model.FormaPagamentoDto;
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
public interface RestauranteFormaPagamentoControllerOpenApi {
    @ApiModelProperty("Lista as formas de pagamento associadas a um restaurante")
    @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = Problem.class)
    ))
    CollectionModel<FormaPagamentoDto> listar(@ApiParam(value = "ID do restaurante", example = "1", required = true)
                                   Long restauranteId);

    @ApiModelProperty("Associa uma forma de pagamento a um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Associação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante ou forma de pagamento não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Problem.class)
            ))
    })
    ResponseEntity<Void> associar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
                  @ApiParam(value = "ID da forma de pagamento", example = "1", required = true) Long formaPagamentoId);

    @ApiModelProperty("Desassocia uma forma de pagamento de um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Desassociação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante ou forma de pagamento não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Problem.class)
            ))
    })
    ResponseEntity<Void> desassociar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
                     @ApiParam(value = "ID da forma de pagamento", example = "1", required = true)
                     Long formaPagamentoId);

}
