package br.dev.pauloroberto.algafood.infrastructure.service.report;

import br.dev.pauloroberto.algafood.domain.filter.VendaDiariaFilter;
import br.dev.pauloroberto.algafood.domain.service.VendaQueryService;
import br.dev.pauloroberto.algafood.domain.service.VendaReportService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;

@Service
public class VendaPdfReportServiceImpl implements VendaReportService {
    @Autowired
    private VendaQueryService vendaQueryService;

    @Override
    public byte[] emitirVendasDiarias(VendaDiariaFilter filter, String timeOffset) {
        try {
            InputStream inputStream = this.getClass().getResourceAsStream("/reports/vendas-diarias.jasper");

            var parametros = new HashMap<String, Object>();
            parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));

            var dataSource = new JRBeanCollectionDataSource(
                    vendaQueryService.consultarVendasDiarias(filter, timeOffset)
            );

            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            throw new ReportException("Não foi possível emitir o relatório de vendas diárias", e);
        }
    }

}
