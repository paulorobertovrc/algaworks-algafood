package br.dev.pauloroberto.algafood.domain.service;

import br.dev.pauloroberto.algafood.domain.exception.FotoProdutoNaoEncontradaException;
import br.dev.pauloroberto.algafood.domain.model.FotoProduto;
import br.dev.pauloroberto.algafood.domain.repository.ProdutoRepository;
import br.dev.pauloroberto.algafood.domain.service.FotoStorageService.NovaFoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
public class CatalogoFotoProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private FotoStorageService fotoStorage;

    @Transactional
    public FotoProduto salvarFotoProduto(FotoProduto foto, InputStream dadosArquivo) {
        Long restauranteId = foto.getRestauranteId();
        Long produtoId = foto.getProduto().getId();

        String nomeArquivoExistente = null;
        String nomeNovoArquivo = fotoStorage.gerarNomeArquivo(foto.getNomeArquivo());

        Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);

        if (fotoExistente.isPresent()) {
            nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
            produtoRepository.delete(fotoExistente.get());
        }

        foto.setNomeArquivo(nomeNovoArquivo);

        produtoRepository.save(foto);
        produtoRepository.flush();

        NovaFoto novaFoto = NovaFoto.builder()
                .nomeArquivo(foto.getNomeArquivo())
                .inputStream(dadosArquivo)
                .contentType(foto.getContentType())
                .build();

        fotoStorage.substituir(nomeArquivoExistente, novaFoto);

        return foto;
    }

    public FotoProduto verificarSeExiste(Long produtoId, Long restauranteId) {
        return produtoRepository.findFotoById(restauranteId, produtoId)
                .orElseThrow(() -> new FotoProdutoNaoEncontradaException(produtoId, restauranteId));
    }
}
