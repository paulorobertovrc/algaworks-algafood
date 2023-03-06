package br.dev.pauloroberto.algafood.domain.service;

import br.dev.pauloroberto.algafood.domain.exception.NegocioException;
import br.dev.pauloroberto.algafood.domain.exception.SenhaUsuarioNaoCoincideException;
import br.dev.pauloroberto.algafood.domain.exception.UsuarioNaoEncontradoException;
import br.dev.pauloroberto.algafood.domain.model.Grupo;
import br.dev.pauloroberto.algafood.domain.model.Usuario;
import br.dev.pauloroberto.algafood.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CadastroUsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CadastroGrupoService cadastroGrupoService;

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario verificarSeExiste(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        usuarioRepository.detach(usuario);

        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
            throw new NegocioException(
                    String.format("Já existe um usuário cadastrado com o e-mail %s", usuario.getEmail()));
        }

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void remover(Long id) {
        try {
            usuarioRepository.deleteById(id);
            usuarioRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new UsuarioNaoEncontradoException(id);
        }
    }

    @Transactional
    public void alterarSenha(Long id, String senhaAtual, String novaSenha) {
        Usuario usuario = verificarSeExiste(id);

        if (usuario.senhaNaoCoincideCom(senhaAtual)) {
            throw new SenhaUsuarioNaoCoincideException();
        }

        usuario.setSenha(novaSenha);
    }

    @Transactional
    public void associarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = verificarSeExiste(usuarioId);
        Grupo grupo = cadastroGrupoService.verificarSeExiste(grupoId);

        if (usuario.jaEstaAssociadoAoGrupo(grupo)) {
            throw new NegocioException(
                    String.format("Usuário de código %d já está associado ao grupo de código %d", usuarioId, grupoId));
        }

        usuario.associarGrupo(grupo);
    }

    @Transactional
    public void desassociarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = verificarSeExiste(usuarioId);
        Grupo grupo = cadastroGrupoService.verificarSeExiste(grupoId);

        if (!usuario.jaEstaAssociadoAoGrupo(grupo)) {
            throw new NegocioException(
                    String.format("Usuário de código %d não está associado ao grupo de código %d", usuarioId, grupoId));
        }

        usuario.desassociarGrupo(grupo);
    }

}
