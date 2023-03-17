package br.dev.pauloroberto.algafood.domain.service;

import br.dev.pauloroberto.algafood.domain.filter.VendaDiariaFilter;

public interface VendaReportService {
    byte[] emitirVendasDiarias(VendaDiariaFilter filter, String timeOffset);
}
