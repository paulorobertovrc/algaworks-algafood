package br.dev.pauloroberto.algafood.api.controller;

import br.dev.pauloroberto.algafood.api.assembler.ProdutoDomainObjectAssembler;
import br.dev.pauloroberto.algafood.api.assembler.ProdutoDtoAssembler;
import br.dev.pauloroberto.algafood.api.helper.LinkHelper;
import br.dev.pauloroberto.algafood.api.model.ProdutoDto;
import br.dev.pauloroberto.algafood.api.model.input.ProdutoInputDto;
import br.dev.pauloroberto.algafood.api.openapi.controller.RestauranteProdutoControllerOpenApi;
import br.dev.pauloroberto.algafood.domain.model.Produto;
import br.dev.pauloroberto.algafood.domain.model.Restaurante;
import br.dev.pauloroberto.algafood.domain.service.CadastroProdutoService;
import br.dev.pauloroberto.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
//@ApiIgnore // Desnecessária após a criação do RestauranteProdutoControllerOpenApi
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;
    @Autowired
    private CadastroProdutoService cadastroProdutoService;
    @Autowired
    private ProdutoDtoAssembler produtoDtoAssembler;
    @Autowired
    private ProdutoDomainObjectAssembler produtoDomainObjectAssembler;
    @Autowired
    private LinkHelper linkHelper;

    @Override
    @GetMapping
    public CollectionModel<ProdutoDto> listar(@PathVariable Long restauranteId,
                                              @RequestParam(required = false) Boolean incluirInativos) {

        Restaurante restaurante = cadastroRestauranteService.verificarSeExiste(restauranteId);
        List<Produto> todosProdutos = null;

        if (incluirInativos) {
            todosProdutos = cadastroProdutoService.listarTodos(restaurante.getId());
        } else {
            todosProdutos = cadastroProdutoService.listarAtivos(restaurante.getId());
        }

        return produtoDtoAssembler.toCollectionModel(todosProdutos)
                .add(linkHelper.linkToProdutos(restauranteId));
    }

    @GetMapping("/{produtoId}")
    public ProdutoDto buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Produto produto = cadastroProdutoService.verificarSeExiste(produtoId, restauranteId);

        return produtoDtoAssembler.toDto(produto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDto adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInputDto produtoInput) {
        Produto produto = produtoDomainObjectAssembler.toDomainObject(produtoInput);
        produto.setRestaurante(cadastroRestauranteService.verificarSeExiste(restauranteId));

        return produtoDtoAssembler.toDto(cadastroProdutoService.salvar(produto));
    }

    @PutMapping("/{produtoId}")
    public ProdutoDto atualizar(@PathVariable Long restauranteId,
                                @PathVariable Long produtoId,
                                @RequestBody @Valid ProdutoInputDto produtoInput) {
        Produto produtoAtual = cadastroProdutoService.verificarSeExiste(produtoId, restauranteId);
        produtoDomainObjectAssembler.copyToDomainObject(produtoInput, produtoAtual);

        return produtoDtoAssembler.toDto(cadastroProdutoService.salvar(produtoAtual));
    }
}
