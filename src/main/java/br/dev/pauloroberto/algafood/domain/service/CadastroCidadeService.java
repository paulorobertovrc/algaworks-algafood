package br.dev.pauloroberto.algafood.domain.service;

import br.dev.pauloroberto.algafood.domain.exception.CidadeNaoEncontradaException;
import br.dev.pauloroberto.algafood.domain.model.Cidade;
import br.dev.pauloroberto.algafood.domain.model.Estado;
import br.dev.pauloroberto.algafood.domain.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroCidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Estado estado = cadastroEstadoService.verificarSeExiste(estadoId);

        cidade.setEstado(estado);

        return cidadeRepository.save(cidade);
    }

    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }

    public void remover(Long id) {
        cidadeRepository.deleteById(id);
    }

    public Cidade verificarSeExiste(Long cidadeId) {
        return cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId)
                );
    }
}
