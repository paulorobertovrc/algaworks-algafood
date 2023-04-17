package br.dev.pauloroberto.algafood.api.controller;

import br.dev.pauloroberto.algafood.api.assembler.FormaPagamentoDomainObjectAssembler;
import br.dev.pauloroberto.algafood.api.assembler.FormaPagamentoDtoAssembler;
import br.dev.pauloroberto.algafood.api.model.FormaPagamentoDto;
import br.dev.pauloroberto.algafood.api.model.input.FormaPagamentoInputDto;
import br.dev.pauloroberto.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import br.dev.pauloroberto.algafood.domain.exception.NegocioException;
import br.dev.pauloroberto.algafood.domain.model.FormaPagamento;
import br.dev.pauloroberto.algafood.domain.service.CadastroFormaPagamentoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/formas-pagamento")
@Api(tags = "Formas de pagamento")
public class FormaPagamentoController {
    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;
    @Autowired
    private FormaPagamentoDtoAssembler formaPagamentoDtoAssembler;
    @Autowired
    private FormaPagamentoDomainObjectAssembler formaPagamentoDomainObjectAssembler;

    @GetMapping
    @ApiOperation("Lista as formas de pagamento")
    public ResponseEntity<List<FormaPagamentoDto>> listar(ServletWebRequest request) {
        desabilitarShallowETag(request);

        String eTag = definirETag();

        if (eTagInalterada(request, eTag)) return null;

        List<FormaPagamento> formasPagamento = cadastroFormaPagamentoService.listar();
        List<FormaPagamentoDto> formasPagamentoDto = formaPagamentoDtoAssembler.toDtoList(formasPagamento);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .eTag(eTag)
                .body(formasPagamentoDto);
    }

    @GetMapping("/{id}")
    @ApiOperation("Busca uma forma de pagamento por ID")
    public ResponseEntity<FormaPagamentoDto> buscar(@PathVariable Long id, ServletWebRequest request) {
        desabilitarShallowETag(request);

        String eTag = definirETag();

        if (eTagInalterada(request, eTag)) return null;

        FormaPagamento formaPagamento = cadastroFormaPagamentoService.verificarSeExiste(id);
        FormaPagamentoDto formaPagamentoDto = formaPagamentoDtoAssembler.toDto(formaPagamento);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(formaPagamentoDto);
    }

    private static void desabilitarShallowETag(ServletWebRequest request) {
        // Desabilita o ETag gerado pelo ShallowEtagHeaderFilter
        // para que o Deep ETag gerado manualmente seja utilizado
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
    }

    private String definirETag() {
        String eTag;
        OffsetDateTime dataUltimaAtualizacao = cadastroFormaPagamentoService.getDataUltimaAtualizacao();

        if (dataUltimaAtualizacao != null) {
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        } else {
            eTag = "0";
        }

        return eTag;
    }

    private boolean eTagInalterada(ServletWebRequest request, String eTag) {
        return request.checkNotModified(eTag);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Cadastra uma forma de pagamento")
    public FormaPagamentoDto adicionar(@RequestBody @Valid FormaPagamentoInputDto formaPagamentoInput) {
        try {
            FormaPagamento formaPagamento = formaPagamentoDomainObjectAssembler.toDomainObject(formaPagamentoInput);
            return formaPagamentoDtoAssembler.toDto(cadastroFormaPagamentoService.salvar(formaPagamento));
        } catch (FormaPagamentoNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ApiOperation("Atualiza uma forma de pagamento")
    public FormaPagamentoDto atualizar(@PathVariable Long id,
                                       @RequestBody @Valid FormaPagamentoInputDto formaPagamentoInput) {
        try {
            FormaPagamento formaPagamento = cadastroFormaPagamentoService.verificarSeExiste(id);
            formaPagamentoDomainObjectAssembler.copyToDomainObject(formaPagamentoInput, formaPagamento);
            return formaPagamentoDtoAssembler.toDto(cadastroFormaPagamentoService.salvar(formaPagamento));
        } catch (FormaPagamentoNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Remove uma forma de pagamento")
    public void remover(@PathVariable Long id) {
        cadastroFormaPagamentoService.remover(id);
    }
}
