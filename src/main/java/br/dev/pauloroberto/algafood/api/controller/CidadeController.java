package br.dev.pauloroberto.algafood.api.controller;

import br.dev.pauloroberto.algafood.api.assembler.CidadeDomainObjectAssembler;
import br.dev.pauloroberto.algafood.api.assembler.CidadeDtoAssembler;
import br.dev.pauloroberto.algafood.api.helper.ResourceUriHelper;
import br.dev.pauloroberto.algafood.api.model.CidadeDto;
import br.dev.pauloroberto.algafood.api.model.input.CidadeInputDto;
import br.dev.pauloroberto.algafood.api.openapi.controller.CidadeControllerOpenApi;
import br.dev.pauloroberto.algafood.domain.exception.EstadoNaoEncontradoException;
import br.dev.pauloroberto.algafood.domain.exception.NegocioException;
import br.dev.pauloroberto.algafood.domain.model.Cidade;
import br.dev.pauloroberto.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {
    @Autowired
    private CadastroCidadeService cadastroCidadeService;
    @Autowired
    private CidadeDtoAssembler cidadeDtoAssembler;
    @Autowired
    private CidadeDomainObjectAssembler cidadeDomainObjectAssembler;

    @GetMapping
    public CollectionModel<CidadeDto> listar() {
        return cidadeDtoAssembler.toCollectionModel(
                cadastroCidadeService.listar());
    }

    @GetMapping("/{id}")
    public CidadeDto buscar(@PathVariable Long id) {
        return cidadeDtoAssembler.toModel(
                cadastroCidadeService.verificarSeExiste(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDto adicionar(@RequestBody @Valid CidadeInputDto cidadeInput) {
        try {
            Cidade cidade = cidadeDomainObjectAssembler.toDomainObject(cidadeInput);
            CidadeDto cidadeDto = cidadeDtoAssembler.toModel(cadastroCidadeService.salvar(cidade));
            ResourceUriHelper.addUriInResponseHeader(cidadeDto.getId());

            return cidadeDto;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public CidadeDto atualizar(@PathVariable Long id, @RequestBody @Valid CidadeInputDto cidadeInput) {
        try {
        Cidade cidade = cadastroCidadeService.verificarSeExiste(id);

        cidadeDomainObjectAssembler.copyToDomainObject(cidadeInput, cidade);

        return cidadeDtoAssembler.toModel(cadastroCidadeService.salvar(cidade));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cadastroCidadeService.excluir(id);
    }
}
