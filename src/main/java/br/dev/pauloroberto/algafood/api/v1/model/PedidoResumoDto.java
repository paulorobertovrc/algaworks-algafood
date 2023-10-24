package br.dev.pauloroberto.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
//@JsonFilter("pedidoFilter")
@Relation(collectionRelation = "pedidos")
public class PedidoResumoDto extends RepresentationModel<PedidoResumoDto> {
    @ApiModelProperty(example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
    private String codigo;

    @ApiModelProperty(example = "199.00")
    private BigDecimal subtotal;

    @ApiModelProperty(example = "10.00")
    private BigDecimal taxaFrete;

    @ApiModelProperty(example = "209.00")
    private BigDecimal valorTotal;

    @ApiModelProperty(example = "CRIADO")
    private String status;

    @ApiModelProperty(example = "2019-12-01T20:34:04Z [AAAA-MM-DDThh:mm:ssZ]")
    private OffsetDateTime dataCriacao;

    private RestauranteApenasNomeDto restaurante;
    private UsuarioDto cliente;
}
