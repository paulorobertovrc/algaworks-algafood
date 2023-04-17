package br.dev.pauloroberto.algafood.api.controller;

import br.dev.pauloroberto.algafood.api.assembler.CidadeDomainObjectAssembler;
import br.dev.pauloroberto.algafood.api.assembler.CidadeDtoAssembler;
import br.dev.pauloroberto.algafood.api.model.CidadeDto;
import br.dev.pauloroberto.algafood.api.model.input.CidadeInputDto;
import br.dev.pauloroberto.algafood.domain.exception.EstadoNaoEncontradoException;
import br.dev.pauloroberto.algafood.domain.exception.NegocioException;
import br.dev.pauloroberto.algafood.domain.model.Cidade;
import br.dev.pauloroberto.algafood.domain.service.CadastroCidadeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cidades")
@Api(tags = "Cidades")
public class CidadeController {
    @Autowired
    private CadastroCidadeService cadastroCidadeService;
    @Autowired
    private CidadeDtoAssembler cidadeDtoAssembler;
    @Autowired
    private CidadeDomainObjectAssembler cidadeDomainObjectAssembler;

    @ApiOperation("Lista as cidades")
    @GetMapping
    public List<CidadeDto> listar() {
        return cidadeDtoAssembler.toDtoList(cadastroCidadeService.listar());
    }

    @ApiOperation("Busca uma cidade por ID")
    @GetMapping("/{id}")
    public CidadeDto buscar(@ApiParam("ID de uma cidade") @PathVariable Long id) {
        Cidade cidade = cadastroCidadeService.verificarSeExiste(id);

        return cidadeDtoAssembler.toDto(cidade);
    }

    @ApiOperation("Cadastra uma cidade")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDto adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade") @RequestBody
                                   @Valid CidadeInputDto cidadeInput) {
        try {
            Cidade cidade = cidadeDomainObjectAssembler.toDomainObject(cidadeInput);

            return cidadeDtoAssembler.toDto(cadastroCidadeService.salvar(cidade));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @ApiOperation("Atualiza uma cidade")
    @PutMapping("/{id}")
    public CidadeDto atualizar(@ApiParam("ID de uma cidade") @PathVariable Long id,
                            @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados")
                            @RequestBody @Valid CidadeInputDto cidadeInput) {
        try {
        Cidade cidade = cadastroCidadeService.verificarSeExiste(id);

        cidadeDomainObjectAssembler.copyToDomainObject(cidadeInput, cidade);

        return cidadeDtoAssembler.toDto(cadastroCidadeService.salvar(cidade));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @ApiOperation("Exclui uma cidade")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@ApiParam("ID de uma cidade") @PathVariable Long id) {
        cadastroCidadeService.excluir(id);
    }
}
