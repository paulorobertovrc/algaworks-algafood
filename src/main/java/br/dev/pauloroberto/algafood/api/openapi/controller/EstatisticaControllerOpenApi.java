package br.dev.pauloroberto.algafood.api.openapi.controller;

import br.dev.pauloroberto.algafood.domain.filter.VendaDiariaFilter;
import br.dev.pauloroberto.algafood.domain.model.dto.VendaDiaria;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Estatísticas")
public interface EstatisticaControllerOpenApi {
    @ApiOperation("Retorna consulta de vendas diárias no formato JSON")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "restauranteId", value = "ID do restaurante", example = "1", dataType = "int"),
            @ApiImplicitParam(name = "dataCriacaoInicio", value = "Data/hora inicial da criação do pedido",
                    example = "2022-12-01T00:00:00Z", dataType = "date-time"),
            @ApiImplicitParam(name = "dataCriacaoFim", value = "Data/hora final da criação do pedido",
                    example = "2022-12-31T23:59:59Z", dataType = "date-time")
    })
    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter, @ApiParam(
            value = "Diferença de horário em relação ao UTC", defaultValue = "+00:00") String timeOffset);

    ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filter, String timeOffset);

}
