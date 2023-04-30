package br.dev.pauloroberto.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Relation(collectionRelation = "pedidos")
public class PedidoDto extends RepresentationModel<PedidoDto> {
    @ApiModelProperty(example = "d9f5a8f0-7b1f-4b9f-9b9b-9c5c9c5c9c5c")
    private String codigo;

    @ApiModelProperty(example = "298.90")
    private BigDecimal subtotal;

    @ApiModelProperty(example = "10.00")
    private BigDecimal taxaFrete;

    @ApiModelProperty(example = "308.90")
    private BigDecimal valorTotal;

    @ApiModelProperty(example = "CRIADO")
    private String status;

    @ApiModelProperty(example = "2023-04-19T20:34:04Z")
    private OffsetDateTime dataCriacao;

    @ApiModelProperty(example = "2023-04-19T20:35:34Z")
    private OffsetDateTime dataConfirmacao;

    @ApiModelProperty(example = "2023-04-19T21:30:00Z")
    private OffsetDateTime dataEntrega;

    @ApiModelProperty(example = "2023-04-19T21:35:00Z")
    private OffsetDateTime dataCancelamento;

    private RestauranteApenasNomeDto restaurante;
    private FormaPagamentoDto formaPagamento;
    private UsuarioDto cliente;
    private EnderecoDto enderecoEntrega;
    private List<ItemPedidoDto> itens;
}
