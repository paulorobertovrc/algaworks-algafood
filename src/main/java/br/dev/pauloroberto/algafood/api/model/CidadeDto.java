package br.dev.pauloroberto.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeDto {
    private Long id;
    private String nome;
    private EstadoDto estado;
}
