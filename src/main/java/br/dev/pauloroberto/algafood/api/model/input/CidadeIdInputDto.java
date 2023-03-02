package br.dev.pauloroberto.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class CidadeIdInputDto {
    @NotNull
    private Long id;

}
