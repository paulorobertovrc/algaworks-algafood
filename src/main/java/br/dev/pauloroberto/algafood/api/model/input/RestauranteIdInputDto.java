package br.dev.pauloroberto.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class RestauranteIdInputDto {
    @NotNull
    private Long id;
}
