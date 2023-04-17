package br.dev.pauloroberto.algafood.api.controller;

import br.dev.pauloroberto.algafood.api.assembler.GrupoDomainObjectAssembler;
import br.dev.pauloroberto.algafood.api.assembler.GrupoDtoAssembler;
import br.dev.pauloroberto.algafood.api.model.GrupoDto;
import br.dev.pauloroberto.algafood.api.model.input.GrupoInputDto;
import br.dev.pauloroberto.algafood.domain.exception.GrupoNaoEncontradoException;
import br.dev.pauloroberto.algafood.domain.exception.NegocioException;
import br.dev.pauloroberto.algafood.domain.model.Grupo;
import br.dev.pauloroberto.algafood.domain.service.CadastroGrupoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/grupos")
@Api(tags = "Grupos")
public class GrupoController {
    @Autowired
    private CadastroGrupoService cadastroGrupoService;
    @Autowired
    private GrupoDtoAssembler grupoDtoAssembler;
    @Autowired
    private GrupoDomainObjectAssembler grupoDomainObjectAssembler;

    @GetMapping
    @ApiOperation("Lista os grupos")
    public List<GrupoDto> listar() {
        return grupoDtoAssembler.toDtoList(cadastroGrupoService.listar());
    }

    @GetMapping("/{id}")
    @ApiOperation("Busca um grupo por ID")
    public GrupoDto buscar(@PathVariable Long id) {
        return grupoDtoAssembler.toDto(cadastroGrupoService.verificarSeExiste(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Cadastra um grupo")
    public GrupoDto adicionar(@RequestBody @Valid GrupoInputDto grupoInput) {
        Grupo grupo = grupoDomainObjectAssembler.toDomainObject(grupoInput);

        return grupoDtoAssembler.toDto(cadastroGrupoService.salvar(grupo));
    }

    @PutMapping("/{id}")
    @ApiOperation("Atualiza um grupo")
    public GrupoDto atualizar(@PathVariable Long id,
                              @RequestBody @Valid GrupoInputDto grupoInput) {
        try {
            Grupo grupo = cadastroGrupoService.verificarSeExiste(id);
            grupoDomainObjectAssembler.copyToDomainObject(grupoInput, grupo);

            return grupoDtoAssembler.toDto(cadastroGrupoService.salvar(grupo));
        } catch (GrupoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Remove um grupo")
    public void remover(@PathVariable Long id) {
        cadastroGrupoService.remover(id);
    }

}
