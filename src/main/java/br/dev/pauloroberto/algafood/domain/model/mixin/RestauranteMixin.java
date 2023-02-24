package br.dev.pauloroberto.algafood.domain.model.mixin;

import br.dev.pauloroberto.algafood.domain.model.Cozinha;
import br.dev.pauloroberto.algafood.domain.model.Endereco;
import br.dev.pauloroberto.algafood.domain.model.FormaPagamento;
import br.dev.pauloroberto.algafood.domain.model.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class RestauranteMixin {
    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    // Ignora a propriedade nome da cozinha quando serializar o restaurante,
    // mas permite a leitura da propriedade nome na desserialização
    private Cozinha cozinha;
    @JsonIgnore
    private Endereco endereco;
    @JsonIgnore
    private LocalDateTime dataCadastro;
    @JsonIgnore
    private LocalDateTime dataAtualizacao;
    @JsonIgnore
    private List<FormaPagamento> formasPagamento = new ArrayList<>();
    @JsonIgnore
    private List<Produto> produtos = new ArrayList<>();
}
