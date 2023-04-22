package br.dev.pauloroberto.algafood.api.controller;

import br.dev.pauloroberto.algafood.api.assembler.GrupoDtoAssembler;
import br.dev.pauloroberto.algafood.api.model.GrupoDto;
import br.dev.pauloroberto.algafood.api.openapi.controller.UsuarioGrupoControllerOpenApi;
import br.dev.pauloroberto.algafood.domain.model.Usuario;
import br.dev.pauloroberto.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/usuarios/{usuarioId}/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {
    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;
    @Autowired
    private GrupoDtoAssembler grupoDtoAssembler;

    @GetMapping
    public List<GrupoDto> listar(@PathVariable Long usuarioId) {
        Usuario usuario = cadastroUsuarioService.verificarSeExiste(usuarioId);

        return grupoDtoAssembler.toDtoList(usuario.getGrupos());
    }

    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        cadastroUsuarioService.associarGrupo(usuarioId, grupoId);
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        cadastroUsuarioService.desassociarGrupo(usuarioId, grupoId);
    }

}
