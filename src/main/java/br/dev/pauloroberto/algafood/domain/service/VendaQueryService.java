package br.dev.pauloroberto.algafood.domain.service;

import br.dev.pauloroberto.algafood.domain.filter.VendaDiariaFilter;
import br.dev.pauloroberto.algafood.domain.model.dto.VendaDiaria;

import java.util.List;

public interface VendaQueryService {
    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter, String timeOffset);
}
