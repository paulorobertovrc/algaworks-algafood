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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/estados")
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
    public List<EstadoDto> listar() {
        return estadoDtoAssembler.toDtoList(estadoRepository.findAll());
    }

    @GetMapping("/{id}")
    public EstadoDto buscar(@PathVariable Long id) {
        Estado estado = cadastroEstadoService.verificarSeExiste(id);

        return estadoDtoAssembler.toDto(estado);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
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
    public void remover(@PathVariable Long id) {
        cadastroEstadoService.excluir(id);
    }
}
