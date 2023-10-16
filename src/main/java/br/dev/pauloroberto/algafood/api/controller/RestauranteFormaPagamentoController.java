package br.dev.pauloroberto.algafood.api.controller;

import br.dev.pauloroberto.algafood.api.assembler.FormaPagamentoDtoAssembler;
import br.dev.pauloroberto.algafood.api.exception.handler.Problem;
import br.dev.pauloroberto.algafood.api.helper.LinkHelper;
import br.dev.pauloroberto.algafood.api.model.FormaPagamentoDto;
import br.dev.pauloroberto.algafood.api.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import br.dev.pauloroberto.algafood.domain.model.Restaurante;
import br.dev.pauloroberto.algafood.domain.service.CadastroRestauranteService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
//@ApiIgnore // Não é mais necessária porque foi adicionada a anotação @Api(tags = "Restaurantes") na interface
// RestauranteFormaPagamentoControllerOpenApi
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {
    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;
    @Autowired
    private FormaPagamentoDtoAssembler formaPagamentoDtoAssembler;
    @Autowired
    private LinkHelper linkHelper;

    @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(
            schema = @Schema(implementation = Problem.class)
    ))
    @GetMapping
    public CollectionModel<FormaPagamentoDto> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestauranteService.verificarSeExiste(restauranteId);

        return formaPagamentoDtoAssembler.toCollectionModel(restaurante.getFormasPagamento())
                .removeLinks()
                .add(linkHelper.linkToFormasPagamento(restauranteId, "formasPagamento"));
    }

    @PutMapping("/{formaPagamentoId}")
    public void associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);
    }

}
