package br.dev.pauloroberto.algafood.domain.service;

import br.dev.pauloroberto.algafood.domain.exception.EntidadeEmUsoException;
import br.dev.pauloroberto.algafood.domain.exception.EstadoNaoEncontradoException;
import br.dev.pauloroberto.algafood.domain.exception.NegocioException;
import br.dev.pauloroberto.algafood.domain.model.Estado;
import br.dev.pauloroberto.algafood.domain.model.EstadosEnum;
import br.dev.pauloroberto.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class CadastroEstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    @Transactional
    public Estado salvar(Estado estado) {
        try {
            if (estado.getNome().length() > 2) {
                estado.setEstado(Objects.requireNonNull(
                        EstadosEnum.fromDescricao(estado.getNome())
                ));
            } else {
                estado.setEstado(EstadosEnum.valueOf(estado.getNome()));
            }

            return estadoRepository.save(estado);

        } catch (NullPointerException e) {
            throw new NegocioException(
                    String.format("Estado %s não encontrado. Somente podem ser cadastrados estados válidos."
                            + " Por favor, verifique o nome e tente novamente.", estado.getNome()), e);
        } catch (DataIntegrityViolationException e) {
            throw new NegocioException(
                    String.format("Estado %s já cadastrado. Por favor, verifique o nome e tente novamente.",
                            estado.getNome()), e);
        }
    }

    public Estado verificarSeExiste(Long estadoId) {
        return estadoRepository.findById(estadoId)
                .orElseThrow(() -> new EstadoNaoEncontradoException(estadoId)
                );
    }

    @Transactional
    public void excluir(Long id) {
        try {
            estadoRepository.deleteById(id);
            estadoRepository.flush(); // Força a execução da exclusão no banco de dados
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("O Estado de código %d não pode ser removido porque está em uso", id)
            );
        } catch (EmptyResultDataAccessException e) {
            throw new EstadoNaoEncontradoException(id);
        }
    }
}
