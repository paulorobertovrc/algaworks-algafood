package br.dev.pauloroberto.algafood.api.controller;

import br.dev.pauloroberto.algafood.api.assembler.EstadoDomainObjectAssembler;
import br.dev.pauloroberto.algafood.api.assembler.EstadoDtoAssembler;
import br.dev.pauloroberto.algafood.api.model.EstadoDto;
import br.dev.pauloroberto.algafood.api.model.input.EstadoInputDto;
import br.dev.pauloroberto.algafood.domain.exception.EstadoNaoEncontradoException;
import br.dev.pauloroberto.algafood.domain.exception.NegocioException;
import br.dev.pauloroberto.algafood.domain.model.Estado;
import br.dev.pauloroberto.algafood.domain.repository.EstadoRepository;
import br.dev.pauloroberto.algafood.domain.service.CadastroEstadoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/estados")
@Api(tags = "Estados")
public class EstadoController {
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private CadastroEstadoService cadastroEstadoService;
    @Autowired
    private EstadoDtoAssembler estadoDtoAssembler;
    @Autowired
    private EstadoDomainObjectAssembler estadoDomainObjectAssembler;

    @GetMapping
    @ApiOperation("Lista os estados")
    public List<EstadoDto> listar() {
        return estadoDtoAssembler.toDtoList(estadoRepository.findAll());
    }

    @GetMapping("/{id}")
    @ApiOperation("Busca um estado por ID")
    public EstadoDto buscar(@PathVariable Long id) {
        Estado estado = cadastroEstadoService.verificarSeExiste(id);

        return estadoDtoAssembler.toDto(estado);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Cadastra um estado")
    public EstadoDto adicionar(@RequestBody @Valid EstadoInputDto estadoInput) {
        try {
            Estado estado = estadoDomainObjectAssembler.toDomainObject(estadoInput);

            return estadoDtoAssembler.toDto(cadastroEstadoService.salvar(estado));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Remove um estado")
    public void remover(@PathVariable Long id) {
        cadastroEstadoService.excluir(id);
    }
}
