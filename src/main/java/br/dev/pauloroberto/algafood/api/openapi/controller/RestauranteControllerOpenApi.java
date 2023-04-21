package br.dev.pauloroberto.algafood.api.openapi.controller;

import br.dev.pauloroberto.algafood.api.exception.handler.Problem;
import br.dev.pauloroberto.algafood.api.model.RestauranteDto;
import br.dev.pauloroberto.algafood.api.model.input.RestauranteInputDto;
import br.dev.pauloroberto.algafood.api.openapi.model.RestauranteBasicoModelOpenApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.converter.json.MappingJacksonValue;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {
    @ApiOperation(value = "Lista restaurantes", response = RestauranteBasicoModelOpenApi.class)
    @ApiImplicitParam(name = "projecao", value = "Nome da projeção de pedidos",
            allowableValues = "apenas-nome, completo", paramType = "query", type = "string")
    MappingJacksonValue listar(String projecao);

    @ApiOperation("Busca um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID do restaurante inválido", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = Problem.class)
            ))
    })
    RestauranteDto buscar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);

    @ApiOperation("Busca restaurantes por taxa de frete")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Taxa de frete inválida", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = Problem.class)
            ))
    })
    List<RestauranteDto> buscarPorTaxaFrete(@ApiParam(value = "Taxa de frete inicial", example = "1.00",
            required = true) BigDecimal taxaInicial, @ApiParam(value = "Taxa de frete final", example = "2.00",
            required = true) BigDecimal taxaFinal);

    @ApiOperation("Busca restaurantes por nome")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Nome do restaurante inválido", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = Problem.class)
            ))
    })
    List<RestauranteDto> buscarPorNome(@ApiParam(value = "Nome de um restaurante", example = "Thai Gourmet",
            required = true) String nome, @ApiParam(value = "ID de uma cozinha", example = "1", required = true)
    Long cozinhaId);

    @ApiOperation("Busca o primeiro restaurante por nome")
    @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(
            mediaType = "application/json", schema = @Schema(implementation = Problem.class)
    ))
    Optional<RestauranteDto> buscarPrimeiroPorNome(@ApiParam(value = "Nome de um restaurante", example = "Thai Gourmet",
            required = true) String nome);

    @ApiOperation("Busca os dois primeiros restaurantes por nome")
    @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(
            mediaType = "application/json", schema = @Schema(implementation = Problem.class)
    ))
    List<RestauranteDto> buscarTop2PorNome(@ApiParam(value = "Nome de um restaurante", example = "Thai Gourmet",
            required = true) String nome);

    @ApiOperation("Retorna a contagem de restaurantes por cozinha")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID da cozinha inválido", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = Problem.class)
            ))
    })
    int buscarContagemPorCozinha(@ApiParam(value = "ID de uma cozinha", example = "1", required = true)
                                  Long cozinhaId);

    @ApiOperation("Busca restaurantes de forma customizada")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Taxa de frete inválida", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = Problem.class)
            ))
    })
    List<RestauranteDto> buscarCustomizada(@ApiParam(value = "Nome de um restaurante", example = "Thai Gourmet")
                                           String nome, @ApiParam(value = "Taxa de frete inicial", example = "1.00")
        BigDecimal taxaFreteInicial, @ApiParam(value = "Taxa de frete final", example = "2.00")
        BigDecimal taxaFreteFinal);

    @ApiOperation("Busca restaurantes com frete grátis")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Nome do restaurante inválido", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = Problem.class)
            ))
    })
    List<RestauranteDto> restaurantesComFreteGratis(@ApiParam(value = "Nome de um restaurante",
            example = "Thai Gourmet", required = true) String nome);

    @ApiOperation("Busca o primeiro restaurante")
    @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(
            mediaType = "application/json", schema = @Schema(implementation = Problem.class)
    ))
    Optional<RestauranteDto> buscarPrimeiro();

    @ApiOperation("Adiciona um restaurante")
    @ApiResponse(responseCode = "201", description = "Restaurante adicionado")
    RestauranteDto adicionar(@ApiParam(name = "corpo", value = "Representação de um novo restaurante", required = true)
                             RestauranteInputDto restauranteInput);

    @ApiOperation("Atualiza um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = Problem.class)
            ))
    })
    RestauranteDto atualizar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id,
                             @ApiParam(name = "corpo", value = "Representação de um novo restaurante", required = true)
                             RestauranteInputDto restauranteInput);

    @ApiOperation("Ativa um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurante ativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = Problem.class)
            ))
    })
    void ativar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);

    @ApiOperation("Ativa múltiplos restaurantes")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = Problem.class)
            ))
    })
    void ativarMultiplos(List<Long> ids);

    @ApiOperation("Inativa múltiplos restaurantes")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurantes inativados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = Problem.class)
            ))
    })
    void inativarMultiplos(@ApiParam(name = "corpo", value = "IDs de restaurantes", required = true) List<Long> ids);

    @ApiOperation("Abre um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurante aberto com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = Problem.class)
            ))
    })
    void abrir(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);

    @ApiOperation("Fecha um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurante fechado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = Problem.class)
            ))
    })
    void fechar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);

    @ApiOperation("Inativa um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurante inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = Problem.class)
            ))
    })
    void inativar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);

    @ApiOperation("Atualiza parcialmente um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = Problem.class)
            ))
    })
    RestauranteDto atualizarParcial(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id,
                                    @ApiParam(name = "corpo",
                                            value = "Representação de um restaurante com os novos dados",
                                            required = true
                                    ) Map<String, Object> campos, @ApiIgnore HttpServletRequest request);

}
