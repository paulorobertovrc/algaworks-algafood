package br.dev.pauloroberto.algafood.domain.filter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@Setter
@Getter
public class PedidoFilter {
    @ApiModelProperty(example = "1", value = "ID do cliente para filtro da pesquisa")
    private Long clienteId;

    @ApiModelProperty(example = "1", value = "ID do restaurante para filtro da pesquisa")
    private Long restauranteId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @ApiModelProperty(example = "2022-12-01T20:34:00Z", value = "Data/hora de criação inicial para filtro da pesquisa")
    private OffsetDateTime dataCriacaoInicio;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @ApiModelProperty(example = "2022-12-01T20:34:00Z", value = "Data/hora de criação final para filtro da pesquisa")
    private OffsetDateTime dataCriacaoFim;
}
