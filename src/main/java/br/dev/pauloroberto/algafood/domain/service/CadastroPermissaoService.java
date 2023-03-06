package br.dev.pauloroberto.algafood.domain.service;

import br.dev.pauloroberto.algafood.domain.exception.PermissaoNaoEncontradaException;
import br.dev.pauloroberto.algafood.domain.model.Permissao;
import br.dev.pauloroberto.algafood.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class CadastroPermissaoService {
    @Autowired
    private PermissaoRepository permissaoRepository;

    public Permissao verificarSeExiste(Long id) {
        return permissaoRepository.findById(id)
                .orElseThrow(() -> new PermissaoNaoEncontradaException(id));
    }

    public Collection<Permissao> listar() {
        return permissaoRepository.findAll();
    }

    @Transactional
    public Permissao salvar(Permissao permissao) {
        return permissaoRepository.save(permissao);
    }

    @Transactional
    public void remover(Long id) {
        permissaoRepository.deleteById(id);
    }

}
