package br.dev.pauloroberto.algafood.api.controller;

import br.dev.pauloroberto.algafood.api.assembler.PedidoDomainObjectAssembler;
import br.dev.pauloroberto.algafood.api.assembler.PedidoDtoAssembler;
import br.dev.pauloroberto.algafood.api.assembler.PedidoResumoDtoAssembler;
import br.dev.pauloroberto.algafood.api.model.PedidoDto;
import br.dev.pauloroberto.algafood.api.model.PedidoResumoDto;
import br.dev.pauloroberto.algafood.api.model.input.PedidoInputDto;
import br.dev.pauloroberto.algafood.domain.model.Pedido;
import br.dev.pauloroberto.algafood.domain.model.Usuario;
import br.dev.pauloroberto.algafood.domain.repository.filter.PedidoFilter;
import br.dev.pauloroberto.algafood.domain.service.EmissaoPedidoService;
import br.dev.pauloroberto.algafood.infrastructure.repository.spec.PedidoSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private EmissaoPedidoService emissaoPedidoService;
    @Autowired
    private PedidoDtoAssembler pedidoDtoAssembler;
    @Autowired
    private PedidoResumoDtoAssembler pedidoResumoDtoAssembler;
    @Autowired
    private PedidoDomainObjectAssembler pedidoDomainObjectAssembler;

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

    @GetMapping
    public Page<PedidoResumoDto> pesquisar(PedidoFilter filtro, Pageable pageable) {
        Page<Pedido> pedidosPage = emissaoPedidoService.listar(PedidoSpecs.usandoFiltro(filtro), pageable);
        List<PedidoResumoDto> pedidosResumoDto = pedidoResumoDtoAssembler.toDtoList(pedidosPage.getContent());

        return new PageImpl<>(pedidosResumoDto, pageable, pedidosPage.getTotalElements());
    }

    @GetMapping("/{codigoPedido}")
    public PedidoDto buscar(@PathVariable String codigoPedido) {
        Pedido pedido = emissaoPedidoService.verificarSeExiste(codigoPedido);

        return pedidoDtoAssembler.toDto(pedido);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDto adicionar(@RequestBody @Valid PedidoInputDto pedidoInput) {
        Pedido pedido = pedidoDomainObjectAssembler.toDomainObject(pedidoInput);

        // TODO: pegar usu??rio autenticado
        pedido.setCliente(new Usuario());
        pedido.getCliente().setId(1L);

        emissaoPedidoService.emitir(pedido);

        return pedidoDtoAssembler.toDto(pedido);
    }

}
