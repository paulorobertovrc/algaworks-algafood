package br.dev.pauloroberto.algafood.domain.service;

import br.dev.pauloroberto.algafood.domain.exception.ProdutoNaoEncontradoException;
import br.dev.pauloroberto.algafood.domain.model.Produto;
import br.dev.pauloroberto.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastroProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Transactional
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto verificarSeExiste(Long produtoId, Long restauranteId) {
        return produtoRepository.findByRestauranteIdAndId(restauranteId, produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId, restauranteId));
    }

    public List<Produto> listarTodos(Long restauranteId) {
        cadastroRestauranteService.verificarSeExiste(restauranteId);

        return produtoRepository.findAllByRestauranteId(restauranteId);
    }

    public List<Produto> listarAtivos(Long restauranteId) {
        cadastroRestauranteService.verificarSeExiste(restauranteId);

        return produtoRepository.findAllByRestauranteIdAndAtivoTrue(restauranteId);
    }

    public List<Produto> listar(Long restauranteId, boolean incluirInativos) {
        return incluirInativos ? listarTodos(restauranteId) : listarAtivos(restauranteId);
    }

}
