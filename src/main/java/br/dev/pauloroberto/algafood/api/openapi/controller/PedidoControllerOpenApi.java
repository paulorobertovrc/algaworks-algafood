package br.dev.pauloroberto.algafood.api.openapi.controller;

import br.dev.pauloroberto.algafood.api.exception.handler.Problem;
import br.dev.pauloroberto.algafood.api.model.PedidoDto;
import br.dev.pauloroberto.algafood.api.model.PedidoResumoDto;
import br.dev.pauloroberto.algafood.api.model.input.PedidoInputDto;
import br.dev.pauloroberto.algafood.domain.filter.PedidoFilter;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {
    @ApiOperation("Lista os pedidos")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                    name = "campos", paramType = "query", type = "string")
    })
    Page<PedidoResumoDto> pesquisar(PedidoFilter filtro, Pageable pageable);

    @ApiOperation("Busca um pedido por código")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Código do pedido inválido", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            )),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            ))
    })
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                    name = "campos", paramType = "query", type = "string")
    })
    PedidoDto buscar(@ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
                     String codigoPedido);

    @ApiOperation("Registra um pedido")
    @ApiResponse(responseCode = "201", description = "Pedido criado")
    PedidoDto adicionar(@ApiParam(name = "corpo", value = "Representação de um novo pedido")
                        PedidoInputDto pedidoInput);

}
