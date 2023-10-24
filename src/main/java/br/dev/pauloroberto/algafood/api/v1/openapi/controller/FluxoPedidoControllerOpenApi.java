package br.dev.pauloroberto.algafood.api.v1.openapi.controller;

import br.dev.pauloroberto.algafood.api.exception.handler.Problem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Api(tags = "Pedidos")
public interface FluxoPedidoControllerOpenApi {
    @ApiModelProperty("Confirmação de pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pedido confirmado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            ))
    })
    ResponseEntity<Void> confirmar(@ApiParam(value = "Código do pedido", example = "f5a8c2d5-7b8a-4f3e-9c3a-7c5b5d1f2c3d",
            required = true) String codigoPedido);

    @ApiModelProperty("Entrega de pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pedido entregue com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            ))
    })
    ResponseEntity<Void> entregar(@ApiParam(value = "Código do pedido", example = "f5a8c2d5-7b8a-4f3e-9c3a-7c5b5d1f2c3d",
            required = true) String codigoPedido);

    @ApiModelProperty("Cancelamento de pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pedido cancelado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            ))
    })
    ResponseEntity<Void> cancelar(@ApiParam(value = "Código do pedido", example = "f5a8c2d5-7b8a-4f3e-9c3a-7c5b5d1f2c3d",
            required = true) String codigoPedido);
}
