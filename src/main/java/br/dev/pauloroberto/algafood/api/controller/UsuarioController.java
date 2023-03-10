package br.dev.pauloroberto.algafood.api.controller;

import br.dev.pauloroberto.algafood.api.assembler.UsuarioDomainObjectAssembler;
import br.dev.pauloroberto.algafood.api.assembler.UsuarioDtoAssembler;
import br.dev.pauloroberto.algafood.api.model.UsuarioDto;
import br.dev.pauloroberto.algafood.api.model.input.SenhaInputDto;
import br.dev.pauloroberto.algafood.api.model.input.UsuarioComSenhaInputDto;
import br.dev.pauloroberto.algafood.api.model.input.UsuarioInputDto;
import br.dev.pauloroberto.algafood.domain.exception.NegocioException;
import br.dev.pauloroberto.algafood.domain.exception.UsuarioNaoEncontradoException;
import br.dev.pauloroberto.algafood.domain.model.Usuario;
import br.dev.pauloroberto.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;
    @Autowired
    private UsuarioDtoAssembler usuarioDtoAssembler;
    @Autowired
    private UsuarioDomainObjectAssembler usuarioDomainObjectAssembler;

    @GetMapping
    public List<UsuarioDto> listar() {
        return usuarioDtoAssembler.toDtoList(cadastroUsuarioService.listar());
    }

    @GetMapping("/{id}")
    public UsuarioDto buscar(@PathVariable Long id) {
        return usuarioDtoAssembler.toDto(cadastroUsuarioService.verificarSeExiste(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDto adicionar(@RequestBody @Valid UsuarioComSenhaInputDto usuarioInput) {
        Usuario usuario = usuarioDomainObjectAssembler.toDomainObject(usuarioInput);

        return usuarioDtoAssembler.toDto(cadastroUsuarioService.salvar(usuario));
    }

    @PutMapping("/{id}")
    public UsuarioDto atualizar(@PathVariable Long id,
                                @RequestBody @Valid UsuarioInputDto usuarioInput) {
        try {
            Usuario usuario = cadastroUsuarioService.verificarSeExiste(id);
            usuarioDomainObjectAssembler.copyToDomainObject(usuarioInput, usuario);

            return usuarioDtoAssembler.toDto(cadastroUsuarioService.salvar(usuario));
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
