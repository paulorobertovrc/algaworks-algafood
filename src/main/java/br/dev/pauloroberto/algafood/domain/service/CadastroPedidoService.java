package br.dev.pauloroberto.algafood.domain.service;

import br.dev.pauloroberto.algafood.domain.exception.NegocioException;
import br.dev.pauloroberto.algafood.domain.exception.PedidoNaoEncontradoException;
import br.dev.pauloroberto.algafood.domain.model.*;
import br.dev.pauloroberto.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastroPedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;
    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;
    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;
    @Autowired
    private CadastroProdutoService cadastroProdutoService;
    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    public Pedido verificarSeExiste(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));
    }

    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    @Transactional
    public Pedido emitir(Pedido pedido) {
        validarPedido(pedido);
        validarItens(pedido);

        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calcularValorTotal();

        return pedidoRepository.save(pedido);
    }

    private void validarPedido(Pedido pedido) {
        Restaurante restaurante = cadastroRestauranteService.verificarSeExiste(pedido.getRestaurante().getId());
        FormaPagamento formaPagamento = cadastroFormaPagamentoService.verificarSeExiste(pedido.getFormaPagamento().getId());
        Usuario cliente = cadastroUsuarioService.verificarSeExiste(pedido.getCliente().getId());
        Cidade cidade = cadastroCidadeService.verificarSeExiste(pedido.getEnderecoEntrega().getCidade().getId());

        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);
        pedido.setCliente(cliente);

        if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
            throw new NegocioException(
                    String.format("Forma de pagamento '%s' não é aceita por este restaurante.",
                            formaPagamento.getDescricao()));
        }
    }

    private void validarItens(Pedido pedido) {
        pedido.getItens().forEach(item -> {
            Produto produto = cadastroProdutoService.verificarSeExiste(
                    item.getProduto().getId(), pedido.getRestaurante().getId());

            item.setPedido(pedido);
            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
        });
    }

}
