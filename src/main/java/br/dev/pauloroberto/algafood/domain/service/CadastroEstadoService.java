package br.dev.pauloroberto.algafood.domain.service;

import br.dev.pauloroberto.algafood.domain.exception.EstadoNaoEncontradoException;
import br.dev.pauloroberto.algafood.domain.exception.NegocioException;
import br.dev.pauloroberto.algafood.domain.model.Estado;
import br.dev.pauloroberto.algafood.domain.model.EstadosEnum;
import br.dev.pauloroberto.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CadastroEstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public Estado salvar(Estado estado) {
        try {
            if (estado.getNome().length() > 2) {
                estado.setEstado(Objects.requireNonNull(
                        EstadosEnum.fromDescricao(estado.getNome())
                ));
            } else {
                estado.setEstado(EstadosEnum.valueOf(estado.getNome()));
            }
        } catch (NullPointerException e) {
            throw new NegocioException(String.format("Estado %s não encontrado. Somente podem ser cadastrados estados válidos."
                            + " Por favor, verifique o nome e tente novamente.",
                    estado.getNome()), e);
        }

        return estadoRepository.save(estado);
    }

    public Estado verificarSeExiste(Long estadoId) {
        return estadoRepository.findById(estadoId)
                .orElseThrow(() -> new EstadoNaoEncontradoException(estadoId)
                );
    }

    public void excluir(Long id) {
        estadoRepository.deleteById(id);
    }
}
