package br.dev.pauloroberto.algafood.api.model;

import br.dev.pauloroberto.algafood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Setter
@Getter
@JsonView(RestauranteView.Resumo.class) // Esta anotação indica que o campo nome será exibido na representação resumida
@Relation(collectionRelation = "cozinhas")
public class CozinhaDto extends RepresentationModel<CozinhaDto> {
    @ApiModelProperty(example = "1")
    public Long id;

    @ApiModelProperty(example = "Brasileira")
    public String nome;
}
