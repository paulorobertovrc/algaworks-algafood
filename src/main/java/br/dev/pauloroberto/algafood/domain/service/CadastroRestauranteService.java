package br.dev.pauloroberto.algafood.domain.service;

import br.dev.pauloroberto.algafood.domain.exception.RestauranteNaoEncontradoException;
import br.dev.pauloroberto.algafood.domain.model.Cidade;
import br.dev.pauloroberto.algafood.domain.model.Cozinha;
import br.dev.pauloroberto.algafood.domain.model.Restaurante;
import br.dev.pauloroberto.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroRestauranteService {
    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;
    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Long cidadeId = restaurante.getEndereco().getCidade().getId();

        Cozinha cozinha = cadastroCozinhaService.verificarSeExiste(cozinhaId);
        Cidade cidade = cadastroCidadeService.verificarSeExiste(cidadeId);

        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);

        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public void ativar(Long id) {
        Restaurante restaurante = verificarSeExiste(id);
        restaurante.ativar();
    }

    @Transactional
    public void inativar(Long id) {
        Restaurante restaurante = verificarSeExiste(id);
        restaurante.inativar();
    }

    public Restaurante verificarSeExiste(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(id)
                );
    }

}
