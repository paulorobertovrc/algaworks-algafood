package br.dev.pauloroberto.algafood.domain.service;

import br.dev.pauloroberto.algafood.domain.exception.NegocioException;
import br.dev.pauloroberto.algafood.domain.exception.RestauranteNaoEncontradoException;
import br.dev.pauloroberto.algafood.domain.model.Cidade;
import br.dev.pauloroberto.algafood.domain.model.Cozinha;
import br.dev.pauloroberto.algafood.domain.model.FormaPagamento;
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
    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

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

    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = verificarSeExiste(restauranteId);
        FormaPagamento formaPagamento = cadastroFormaPagamentoService.verificarSeExiste(formaPagamentoId);

        if (restaurante.getFormasPagamento().contains(formaPagamento)) {
            throw new NegocioException(String.format("Forma de pagamento de código %d (%s) já associada ao restaurante de código %d",
                    formaPagamento.getId(), formaPagamento.getDescricao(), restaurante.getId()));
        }
        // Alternativamente, o tipo da propriedade formasPagamento poderia ser definido como Set em vez de List,
        // e no FormaPagamentoDtoAssembler o método toDtoList poderia recever uma Collection em vez de uma List.
        // Isso faria com que o Controller retornasse a resposta sem alteração nas formas de pagamento associadas, em vez
        // de lançar a exceção.

        restaurante.adicionarFormaPagamento(formaPagamento);
    }

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = verificarSeExiste(restauranteId);
        FormaPagamento formaPagamento = cadastroFormaPagamentoService.verificarSeExiste(formaPagamentoId);

        restaurante.removerFormaPagamento(formaPagamento);
    }

    @Transactional
    public void abrir(Long id) {
        Restaurante restaurante = verificarSeExiste(id);
        restaurante.abrir();
    }

    @Transactional
    public void fechar(Long id) {
        Restaurante restaurante = verificarSeExiste(id);
        restaurante.fechar();
    }

}
