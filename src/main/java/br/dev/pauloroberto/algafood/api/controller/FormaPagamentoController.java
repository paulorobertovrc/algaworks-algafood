package br.dev.pauloroberto.algafood.api.controller;

import br.dev.pauloroberto.algafood.api.assembler.FormaPagamentoDomainObjectAssembler;
import br.dev.pauloroberto.algafood.api.assembler.FormaPagamentoDtoAssembler;
import br.dev.pauloroberto.algafood.api.model.FormaPagamentoDto;
import br.dev.pauloroberto.algafood.api.model.input.FormaPagamentoInputDto;
import br.dev.pauloroberto.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import br.dev.pauloroberto.algafood.domain.exception.NegocioException;
import br.dev.pauloroberto.algafood.domain.model.FormaPagamento;
import br.dev.pauloroberto.algafood.domain.service.CadastroFormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {
    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;
    @Autowired
    private FormaPagamentoDtoAssembler formaPagamentoDtoAssembler;
    @Autowired
    private FormaPagamentoDomainObjectAssembler formaPagamentoDomainObjectAssembler;

    @GetMapping
    public ResponseEntity<List<FormaPagamentoDto>> listar() {
        List<FormaPagamento> formasPagamento = cadastroFormaPagamentoService.listar();
        List<FormaPagamentoDto> formasPagamentoDto = formaPagamentoDtoAssembler.toDtoList(formasPagamento);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(formasPagamentoDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamentoDto> buscar(@PathVariable Long id) {
        FormaPagamento formaPagamento = cadastroFormaPagamentoService.verificarSeExiste(id);
        FormaPagamentoDto formaPagamentoDto = formaPagamentoDtoAssembler.toDto(formaPagamento);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(formaPagamentoDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoDto adicionar(@RequestBody @Valid FormaPagamentoInputDto formaPagamentoInput) {
        try {
            FormaPagamento formaPagamento = formaPagamentoDomainObjectAssembler.toDomainObject(formaPagamentoInput);
            return formaPagamentoDtoAssembler.toDto(cadastroFormaPagamentoService.salvar(formaPagamento));
        } catch (FormaPagamentoNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
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
    public void remover(@PathVariable Long id) {
        cadastroFormaPagamentoService.remover(id);
    }
}
