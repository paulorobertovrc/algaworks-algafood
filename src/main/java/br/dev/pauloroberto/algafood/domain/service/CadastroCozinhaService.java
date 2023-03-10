package br.dev.pauloroberto.algafood.domain.service;

import br.dev.pauloroberto.algafood.domain.exception.CozinhaNaoEncontradaException;
import br.dev.pauloroberto.algafood.domain.exception.EntidadeEmUsoException;
import br.dev.pauloroberto.algafood.domain.model.Cozinha;
import br.dev.pauloroberto.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroCozinhaService {
    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Transactional
    public Cozinha salvar(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    @Transactional
    public void excluir(Long id) {
        try {
            cozinhaRepository.deleteById(id);
            cozinhaRepository.flush(); // Força a execução da exclusão no banco de dados
        } catch (EmptyResultDataAccessException e) {
            throw new CozinhaNaoEncontradaException(id);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("A Cozinha de código %d não pode ser removida porque está em uso", id)
            );
        }
    }

    public Cozinha verificarSeExiste(Long id) {
        return cozinhaRepository.findById(id)
                .orElseThrow(() -> new CozinhaNaoEncontradaException(id)
                );
    }
}
