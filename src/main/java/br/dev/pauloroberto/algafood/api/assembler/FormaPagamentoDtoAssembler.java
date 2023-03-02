package br.dev.pauloroberto.algafood.api.assembler;

import br.dev.pauloroberto.algafood.api.model.FormaPagamentoDto;
import br.dev.pauloroberto.algafood.api.model.input.FormaPagamentoInputDto;
import br.dev.pauloroberto.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FormaPagamentoDtoAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamentoDto toDto(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoDto.class);
    }

    public List<FormaPagamentoDto> toDtoList(List<FormaPagamento> formasPagamento) {
        return formasPagamento.stream()
                .map(this::toDto)
                .toList();
    }

    public FormaPagamentoInputDto toInputDto(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoInputDto.class);
    }
}
