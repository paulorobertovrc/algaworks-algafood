package br.dev.pauloroberto.algafood.api.controller;

import br.dev.pauloroberto.algafood.api.assembler.PermissaoDtoAssembler;
import br.dev.pauloroberto.algafood.api.model.PermissaoDto;
import br.dev.pauloroberto.algafood.domain.model.Grupo;
import br.dev.pauloroberto.algafood.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {
    @Autowired
    private CadastroGrupoService cadastroGrupoService;
    @Autowired
    private PermissaoDtoAssembler permissaoDtoAssembler;

    @GetMapping
    public List<PermissaoDto> listar(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupoService.verificarSeExiste(grupoId);

        return permissaoDtoAssembler.toDtoList(grupo.getPermissoes());
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        cadastroGrupoService.associarPermissao(grupoId, permissaoId);
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        cadastroGrupoService.desassociarPermissao(grupoId, permissaoId);
    }

}
