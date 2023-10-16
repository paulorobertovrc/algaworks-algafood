package br.dev.pauloroberto.algafood.api.assembler;

import br.dev.pauloroberto.algafood.api.helper.LinkHelper;
import br.dev.pauloroberto.algafood.api.model.FormaPagamentoDto;
import br.dev.pauloroberto.algafood.api.model.input.FormaPagamentoInputDto;
import br.dev.pauloroberto.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FormaPagamentoDtoAssembler extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoDto> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinkHelper linkHelper;

    public FormaPagamentoDtoAssembler() {
        super(FormaPagamentoDto.class, FormaPagamentoDto.class);
    }

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

    @Override
    public FormaPagamentoDto toModel(FormaPagamento entity) {
        FormaPagamentoDto formaPagamentoDto = createModelWithId(entity.getId(), entity);
        modelMapper.map(entity, formaPagamentoDto);
        formaPagamentoDto.add(linkHelper.linkToFormasPagamento(formaPagamentoDto.getId(), "formasPagamento"));

        return formaPagamentoDto;
    }

}
