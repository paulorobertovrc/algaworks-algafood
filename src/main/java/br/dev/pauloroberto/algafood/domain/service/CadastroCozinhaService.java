package br.dev.pauloroberto.algafood.domain.service;

import br.dev.pauloroberto.algafood.domain.exception.EntidadeEmUsoException;
import br.dev.pauloroberto.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.dev.pauloroberto.algafood.domain.model.Cozinha;
import br.dev.pauloroberto.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroCozinhaService {
    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Cozinha salvar(Cozinha cozinha) {
        return cozinhaRepository.salvar(cozinha);
    }

    public void excluir(Long id) {
        try {
            cozinhaRepository.remover(id);

        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format("A Cozinha de código %d não existe.", id)
            );

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("A Cozinha de código %d não pode ser removida porque está em uso.", id)
            );
        }
    }
}
