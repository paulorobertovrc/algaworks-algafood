package br.dev.pauloroberto.algafood.api.v1.controller;

import br.dev.pauloroberto.algafood.api.exception.handler.Problem;
import br.dev.pauloroberto.algafood.api.v1.assembler.FormaPagamentoDtoAssembler;
import br.dev.pauloroberto.algafood.api.v1.helper.LinkHelper;
import br.dev.pauloroberto.algafood.api.v1.model.FormaPagamentoDto;
import br.dev.pauloroberto.algafood.api.v1.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import br.dev.pauloroberto.algafood.domain.model.Restaurante;
import br.dev.pauloroberto.algafood.domain.service.CadastroRestauranteService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
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

        CollectionModel<FormaPagamentoDto> formasPagamentoDto = formaPagamentoDtoAssembler
                .toCollectionModel(restaurante.getFormasPagamento())
                .removeLinks()
                .add(linkHelper.linkToFormasPagamento(restauranteId, "formasPagamento"))
                .add(linkHelper.linkToRestauranteFormaPagamentoAssociacao(restauranteId, "associar"));

        formasPagamentoDto.getContent().forEach(formaPagamentoDto ->
                formaPagamentoDto.add(linkHelper.linkToRestauranteFormaPagamentoDesassociacao(
                        restauranteId, formaPagamentoDto.getId(), "desassociar")));

        return formasPagamentoDto;
    }

    @Override
    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);
        return ResponseEntity.noContent().build();
    }

}
