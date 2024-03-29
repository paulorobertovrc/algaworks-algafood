package br.dev.pauloroberto.algafood.api.v1.openapi.controller;

import br.dev.pauloroberto.algafood.api.exception.handler.Problem;
import br.dev.pauloroberto.algafood.api.v1.model.FormaPagamentoDto;
import br.dev.pauloroberto.algafood.api.v1.model.input.FormaPagamentoInputDto;
import br.dev.pauloroberto.algafood.api.v1.openapi.model.FormasPagamentoModelOpenApi;
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
import org.springframework.web.context.request.ServletWebRequest;

@Api(tags = "Formas de Pagamento")
public interface FormaPagamentoControllerOpenApi {
    @ApiOperation(value = "Lista as formas de pagamento", response = FormasPagamentoModelOpenApi.class)
    ResponseEntity<CollectionModel<FormaPagamentoDto>> listar(ServletWebRequest request);

    @ApiOperation(value = "Busca uma forma de pagamento por ID", response = FormasPagamentoModelOpenApi.class)
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID da cozinha inválido", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            )),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            ))
    })
    ResponseEntity<FormaPagamentoDto> buscar(@ApiParam(value = "ID de uma forma de pagamento", example = "1",
            required = true) Long id, ServletWebRequest request);

    @ApiOperation("Cadastra uma forma de pagamento")
    @ApiResponse(responseCode = "201", description = "Forma de pagamento cadastrada")
    FormaPagamentoDto adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova forma de pagamento",
            required = true) FormaPagamentoInputDto formaPagamentoInput);

    @ApiOperation("Atualiza uma forma de pagamento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Forma de pagamento atualizada"),
            @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            ))
    })
    FormaPagamentoDto atualizar(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true)
                                Long id, @ApiParam(name = "corpo", value = "Representação de uma nova forma com os" +
            " novos dados", required = true) FormaPagamentoInputDto formaPagamentoInput);

    @ApiOperation("Remove uma forma de pagamento")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Forma de pagamento removida"),
            @ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            ))
    })
    void remover(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long id);

}
