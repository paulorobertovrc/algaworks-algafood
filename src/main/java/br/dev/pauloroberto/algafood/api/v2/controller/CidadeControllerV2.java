package br.dev.pauloroberto.algafood.api.v2.controller;

import br.dev.pauloroberto.algafood.api.v2.assembler.CidadeDomainObjectAssemblerV2;
import br.dev.pauloroberto.algafood.api.v2.assembler.CidadeDtoAssemblerV2;
import br.dev.pauloroberto.algafood.api.v2.model.CidadeDtoV2;
import br.dev.pauloroberto.algafood.api.v2.model.input.CidadeInputDtoV2;
import br.dev.pauloroberto.algafood.core.helper.ResourceUriHelper;
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
@RequestMapping(path = "/v2/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeControllerV2 {

    @Autowired
    private CadastroCidadeService cadastroCidadeService;
    @Autowired
    private CidadeDtoAssemblerV2 cidadeDtoAssembler;
    @Autowired
    private CidadeDomainObjectAssemblerV2 cidadeDomainObjectAssembler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<CidadeDtoV2> listar() {
        return cidadeDtoAssembler.toCollectionModel(
                cadastroCidadeService.listar());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CidadeDtoV2 buscar(@PathVariable Long id) {
        return cidadeDtoAssembler.toModel(
                cadastroCidadeService.verificarSeExiste(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDtoV2 adicionar(@RequestBody @Valid CidadeInputDtoV2 cidadeInput) {
        try {
            Cidade cidade = cidadeDomainObjectAssembler.toDomainObject(cidadeInput);
            CidadeDtoV2 cidadeDto = cidadeDtoAssembler.toModel(cadastroCidadeService.salvar(cidade));
            ResourceUriHelper.addUriInResponseHeader(cidadeDto.getIdCidade());

            return cidadeDto;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CidadeDtoV2 atualizar(@PathVariable Long id, @RequestBody @Valid CidadeInputDtoV2 cidadeInput) {
        try {
        Cidade cidade = cadastroCidadeService.verificarSeExiste(id);

        cidadeDomainObjectAssembler.copyToDomainObject(cidadeInput, cidade);

        return cidadeDtoAssembler.toModel(cadastroCidadeService.salvar(cidade));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

}
