package br.dev.pauloroberto.algafood.api.v1.controller;

import br.dev.pauloroberto.algafood.api.v1.helper.LinkHelper;
import br.dev.pauloroberto.algafood.api.v1.openapi.controller.EstatisticaControllerOpenApi;
import br.dev.pauloroberto.algafood.domain.filter.VendaDiariaFilter;
import br.dev.pauloroberto.algafood.domain.model.dto.VendaDiaria;
import br.dev.pauloroberto.algafood.domain.service.VendaQueryService;
import br.dev.pauloroberto.algafood.domain.service.VendaReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/estatisticas")
public class EstatisticaController implements EstatisticaControllerOpenApi {

    @Autowired
    private VendaQueryService vendaQueryService;
    @Autowired
    private VendaReportService vendaReportService;
    @Autowired
    private LinkHelper linkHelper;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public EstatisticasModel estatisticas() {
        EstatisticasModel estatisticasModel = new EstatisticasModel();
        estatisticasModel.add(linkHelper.linkToEstatisticasVendasDiarias("vendas-diarias"));

        return estatisticasModel;
    }

    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter, @RequestParam(required = false,
            defaultValue = "+00:00") String timeOffset) {
        return vendaQueryService.consultarVendasDiarias(filter, timeOffset);
    }

    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filter, @RequestParam(required = false,
            defaultValue = "+00:00") String timeOffset) {
        byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filter, timeOffset);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header("Content-Disposition", "attachment; filename=vendas-diarias.pdf")
                .body(bytesPdf);
    }

    public static class EstatisticasModel extends RepresentationModel<EstatisticasModel> {
    }

}
