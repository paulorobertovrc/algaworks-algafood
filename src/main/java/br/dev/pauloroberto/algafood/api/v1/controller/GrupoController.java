package br.dev.pauloroberto.algafood.api.v1.controller;

import br.dev.pauloroberto.algafood.api.v1.assembler.GrupoDomainObjectAssembler;
import br.dev.pauloroberto.algafood.api.v1.assembler.GrupoDtoAssembler;
import br.dev.pauloroberto.algafood.api.v1.model.GrupoDto;
import br.dev.pauloroberto.algafood.api.v1.model.input.GrupoInputDto;
import br.dev.pauloroberto.algafood.api.v1.openapi.controller.GrupoControllerOpenApi;
import br.dev.pauloroberto.algafood.domain.exception.GrupoNaoEncontradoException;
import br.dev.pauloroberto.algafood.domain.exception.NegocioException;
import br.dev.pauloroberto.algafood.domain.model.Grupo;
import br.dev.pauloroberto.algafood.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {
    @Autowired
    private CadastroGrupoService cadastroGrupoService;
    @Autowired
    private GrupoDtoAssembler grupoDtoAssembler;
    @Autowired
    private GrupoDomainObjectAssembler grupoDomainObjectAssembler;

    @Override
    @GetMapping
    public CollectionModel<GrupoDto> listar() {
        List<Grupo> grupos = cadastroGrupoService.listar();

        return grupoDtoAssembler.toCollectionModel(grupos);
    }

    @GetMapping("/{id}")
    public GrupoDto buscar(@PathVariable Long id) {
        return grupoDtoAssembler.toDto(cadastroGrupoService.verificarSeExiste(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDto adicionar(@RequestBody @Valid GrupoInputDto grupoInput) {
        Grupo grupo = grupoDomainObjectAssembler.toDomainObject(grupoInput);

        return grupoDtoAssembler.toDto(cadastroGrupoService.salvar(grupo));
    }

    @PutMapping("/{id}")
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
    public void remover(@PathVariable Long id) {
        cadastroGrupoService.remover(id);
    }

}
