package br.dev.pauloroberto.algafood.api.v1.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "formasPagamento")
public class FormaPagamentoDto extends RepresentationModel<FormaPagamentoDto> {
    private Long id;
    private String descricao;
}
