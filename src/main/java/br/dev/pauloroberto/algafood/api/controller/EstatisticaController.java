package br.dev.pauloroberto.algafood.api.controller;

import br.dev.pauloroberto.algafood.domain.filter.VendaDiariaFilter;
import br.dev.pauloroberto.algafood.domain.model.dto.VendaDiaria;
import br.dev.pauloroberto.algafood.domain.service.VendaQueryService;
import br.dev.pauloroberto.algafood.domain.service.VendaReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticaController {
    @Autowired
    private VendaQueryService vendaQueryService;
    @Autowired
    private VendaReportService vendaReportService;

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

}
