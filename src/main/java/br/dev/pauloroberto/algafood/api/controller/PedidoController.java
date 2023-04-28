package br.dev.pauloroberto.algafood.api.controller;

import br.dev.pauloroberto.algafood.api.assembler.PedidoDomainObjectAssembler;
import br.dev.pauloroberto.algafood.api.assembler.PedidoDtoAssembler;
import br.dev.pauloroberto.algafood.api.assembler.PedidoResumoDtoAssembler;
import br.dev.pauloroberto.algafood.api.model.PedidoDto;
import br.dev.pauloroberto.algafood.api.model.PedidoResumoDto;
import br.dev.pauloroberto.algafood.api.model.input.PedidoInputDto;
import br.dev.pauloroberto.algafood.api.openapi.controller.PedidoControllerOpenApi;
import br.dev.pauloroberto.algafood.core.data.PageableTranslator;
import br.dev.pauloroberto.algafood.domain.filter.PedidoFilter;
import br.dev.pauloroberto.algafood.domain.model.Pedido;
import br.dev.pauloroberto.algafood.domain.model.Usuario;
import br.dev.pauloroberto.algafood.domain.service.EmissaoPedidoService;
import br.dev.pauloroberto.algafood.infrastructure.repository.spec.PedidoSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(path = "/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {
    @Autowired
    private EmissaoPedidoService emissaoPedidoService;
    @Autowired
    private PedidoDtoAssembler pedidoDtoAssembler;
    @Autowired
    private PedidoResumoDtoAssembler pedidoResumoDtoAssembler;
    @Autowired
    private PedidoDomainObjectAssembler pedidoDomainObjectAssembler;
    @Autowired
    private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

//    @GetMapping
//    public MappingJacksonValue pesquisar(@RequestParam(required = false) String campos) {
//        List<Pedido> pedidos = emissaoPedidoService.pesquisar();
//        List<PedidoResumoDto> pedidosResumoDto = pedidoResumoDtoAssembler.toDtoList(pedidos);
//
//        MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosResumoDto);
//        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//
//        if (StringUtils.isNotBlank(campos)) {
//            filterProvider.addFilter("pedidoFilter",
//                    SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
//        }
//        else {
//            filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//        }
//
//        pedidosWrapper.setFilters(filterProvider);
//
//        return pedidosWrapper;
//    }

    @Override
    @GetMapping
    public PagedModel<PedidoResumoDto> pesquisar(PedidoFilter filtro, Pageable pageable) {
        pageable = traduzirPageable(pageable);

        Page<Pedido> pedidosPage = emissaoPedidoService.listar(PedidoSpecs.usandoFiltro(filtro), pageable);

        return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoDtoAssembler);
    }

    @GetMapping("/{codigoPedido}")
    public PedidoDto buscar(@PathVariable String codigoPedido) {
        Pedido pedido = emissaoPedidoService.verificarSeExiste(codigoPedido);

        return pedidoDtoAssembler.toModel(pedido);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDto adicionar(@RequestBody @Valid PedidoInputDto pedidoInput) {
        Pedido pedido = pedidoDomainObjectAssembler.toDomainObject(pedidoInput);

        // TODO: pegar usuário autenticado
        pedido.setCliente(new Usuario());
        pedido.getCliente().setId(1L);

        emissaoPedidoService.emitir(pedido);

        return pedidoDtoAssembler.toModel(pedido);
    }

    private Pageable traduzirPageable(Pageable apiPageable) {
        Map<String, String> mapeamento = Map.of(
                "codigo", "codigo",
                "valorTotal", "valorTotal",
                "restauranteNome", "restaurante.nome",
                "clienteNome", "cliente.nome"
        );

        return PageableTranslator.translate(apiPageable, mapeamento);
    }

}
