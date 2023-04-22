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
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public List<CidadeDto> listar() {
        return cidadeDtoAssembler.toDtoList(cadastroCidadeService.listar());
    }

    @GetMapping("/{id}")
    public CidadeDto buscar(@PathVariable Long id) {
        Cidade cidade = cadastroCidadeService.verificarSeExiste(id);

        CidadeDto cidadeDto = cidadeDtoAssembler.toDto(cidade);
        cidadeDto.add(Link.of("http://localhost:8080/cidades/1"));
        cidadeDto.add(Link.of("http://localhost:8080/cidades", "cidades"));
//        cidadeDto.add(Link.of("http://localhost:8080/cidades", IanaLinkRelations.COLLECTION));
        cidadeDto.getEstado().add(Link.of("http://localhost:8080/estados/1"));

        return cidadeDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDto adicionar(@RequestBody @Valid CidadeInputDto cidadeInput) {
        try {
            Cidade cidade = cidadeDomainObjectAssembler.toDomainObject(cidadeInput);
            CidadeDto cidadeDto = cidadeDtoAssembler.toDto(cadastroCidadeService.salvar(cidade));
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

        return cidadeDtoAssembler.toDto(cadastroCidadeService.salvar(cidade));
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
