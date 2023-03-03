package br.dev.pauloroberto.algafood.domain.service;

import br.dev.pauloroberto.algafood.domain.exception.EntidadeEmUsoException;
import br.dev.pauloroberto.algafood.domain.exception.GrupoNaoEncontradoException;
import br.dev.pauloroberto.algafood.domain.model.Grupo;
import br.dev.pauloroberto.algafood.domain.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastroGrupoService {
    @Autowired
    private GrupoRepository grupoRepository;

    public List<Grupo> listar() {
        return grupoRepository.findAll();
    }

    public Grupo verificarSeExiste(Long id) {
        return grupoRepository.findById(id)
                .orElseThrow(() -> new GrupoNaoEncontradoException(id));
    }

    @Transactional
    public Grupo salvar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    @Transactional
    public void remover(Long id) {
        try {
            grupoRepository.deleteById(id);
            grupoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new GrupoNaoEncontradoException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Grupo de código %d não pode ser removido porque está em uso", id)
            );
        }
    }

}
