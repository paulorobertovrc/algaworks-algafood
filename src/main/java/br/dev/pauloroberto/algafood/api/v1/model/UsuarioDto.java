package br.dev.pauloroberto.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "usuarios")
public class UsuarioDto extends RepresentationModel<UsuarioDto> {
    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Paulo Roberto")
    private String nome;

    @ApiModelProperty(example = "paulo@email.com")
    private String email;
}
