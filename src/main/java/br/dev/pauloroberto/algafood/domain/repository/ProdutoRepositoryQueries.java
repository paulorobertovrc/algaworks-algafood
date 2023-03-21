package br.dev.pauloroberto.algafood.domain.repository;

import br.dev.pauloroberto.algafood.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {
    FotoProduto save(FotoProduto foto);
    void delete(FotoProduto foto);
}
