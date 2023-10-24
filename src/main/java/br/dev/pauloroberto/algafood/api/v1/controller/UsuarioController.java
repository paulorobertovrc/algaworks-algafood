package br.dev.pauloroberto.algafood.api.v1.controller;

import br.dev.pauloroberto.algafood.api.v1.assembler.UsuarioDomainObjectAssembler;
import br.dev.pauloroberto.algafood.api.v1.assembler.UsuarioDtoAssembler;
import br.dev.pauloroberto.algafood.api.v1.model.UsuarioDto;
import br.dev.pauloroberto.algafood.api.v1.model.input.SenhaInputDto;
import br.dev.pauloroberto.algafood.api.v1.model.input.UsuarioComSenhaInputDto;
import br.dev.pauloroberto.algafood.api.v1.model.input.UsuarioInputDto;
import br.dev.pauloroberto.algafood.api.v1.openapi.controller.UsuarioControllerOpenApi;
import br.dev.pauloroberto.algafood.domain.exception.NegocioException;
import br.dev.pauloroberto.algafood.domain.exception.UsuarioNaoEncontradoException;
import br.dev.pauloroberto.algafood.domain.model.Usuario;
import br.dev.pauloroberto.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController implements UsuarioControllerOpenApi {
    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;
    @Autowired
    private UsuarioDtoAssembler usuarioDtoAssembler;
    @Autowired
    private UsuarioDomainObjectAssembler usuarioDomainObjectAssembler;

    @GetMapping
    public CollectionModel<UsuarioDto> listar() {
        return usuarioDtoAssembler.toCollectionModel(
                cadastroUsuarioService.listar());
    }

    @GetMapping("/{id}")
    public UsuarioDto buscar(@PathVariable Long id) {
        return usuarioDtoAssembler.toModel(
                cadastroUsuarioService.verificarSeExiste(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDto adicionar(@RequestBody @Valid UsuarioComSenhaInputDto usuarioInput) {
        Usuario usuario = usuarioDomainObjectAssembler.toDomainObject(usuarioInput);

        return usuarioDtoAssembler.toModel(cadastroUsuarioService.salvar(usuario));
    }

    @PutMapping("/{id}")
    public UsuarioDto atualizar(@PathVariable Long id,
                                @RequestBody @Valid UsuarioInputDto usuarioInput) {
        try {
            Usuario usuario = cadastroUsuarioService.verificarSeExiste(id);
            usuarioDomainObjectAssembler.copyToDomainObject(usuarioInput, usuario);

            return usuarioDtoAssembler.toModel(cadastroUsuarioService.salvar(usuario));
        } catch (UsuarioNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long id,
                             @RequestBody @Valid SenhaInputDto senhaInput) {
        try {
            Usuario usuario = cadastroUsuarioService.verificarSeExiste(id);

            cadastroUsuarioService.alterarSenha(usuario.getId(), senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
        } catch (UsuarioNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cadastroUsuarioService.remover(id);
    }

}
