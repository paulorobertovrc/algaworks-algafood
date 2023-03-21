package br.dev.pauloroberto.algafood.domain.service;

import br.dev.pauloroberto.algafood.domain.model.FotoProduto;
import br.dev.pauloroberto.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CatalogoFotoProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public FotoProduto salvarFotoProduto(FotoProduto foto) {
        Long restauranteId = foto.getRestauranteId();
        Long produtoId = foto.getProduto().getId();

        Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);
        fotoExistente.ifPresent(fotoProduto -> produtoRepository.delete(fotoExistente.get()));

        return produtoRepository.save(foto);
    }
}
