package br.dev.pauloroberto.algafood.api.model;

import br.dev.pauloroberto.algafood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonView(RestauranteView.Resumo.class) // Esta anotação indica pode ser colocada na classe ou em um atributo para indicar que o atributo
public class CozinhaDto {
    public Long id;
    public String nome;
}
