package br.dev.pauloroberto.algafood.api.v1.openapi.controller;

import br.dev.pauloroberto.algafood.api.exception.handler.Problem;
import br.dev.pauloroberto.algafood.api.v1.model.ProdutoDto;
import br.dev.pauloroberto.algafood.api.v1.model.input.ProdutoInputDto;
import br.dev.pauloroberto.algafood.domain.model.Produto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;

@Api(tags = "Produtos")
public interface RestauranteProdutoControllerOpenApi {
    @ApiModelProperty("Lista os produtos de um restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "ID do restaurante inválido", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Produto.class)
            )),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            ))
    })
    CollectionModel<ProdutoDto> listar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
            @ApiParam(value = "Indica se os restaurantes inativos devem ser incluídos no resultado da listagem",
                    example = "false", defaultValue = "false") Boolean incluirInativos);

    @ApiModelProperty("Busca um produto de um restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "ID do restaurante ou produto inválido",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Produto.class)
            )),
            @ApiResponse(responseCode = "404", description = "Restaurante ou produto não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            ))
    })
    ProdutoDto buscar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
                      @ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId);

    @ApiModelProperty("Cadastra um produto de um restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto cadastrado"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            ))
    })
    ProdutoDto adicionar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
                         @ApiParam(name = "corpo", value = "Representação de um novo produto", required = true)
                         ProdutoInputDto produtoInput);

    @ApiModelProperty("Atualiza um produto de um restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado"),
            @ApiResponse(responseCode = "404", description = "Restaurante ou produto não encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            ))
    })
    ProdutoDto atualizar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
                         @ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId,
                         @ApiParam(name = "corpo", value = "Representação de um produto com os novos dados",
                                 required = true) ProdutoInputDto produtoInput);

}
