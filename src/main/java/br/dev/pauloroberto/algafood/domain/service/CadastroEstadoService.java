package br.dev.pauloroberto.algafood.domain.service;

import br.dev.pauloroberto.algafood.domain.exception.EstadoNaoEncontradoException;
import br.dev.pauloroberto.algafood.domain.model.Estado;
import br.dev.pauloroberto.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroEstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public Estado verificarSeExiste(Long estadoId) {
        return estadoRepository.findById(estadoId)
                .orElseThrow(() -> new EstadoNaoEncontradoException(estadoId)
                );
    }
}
