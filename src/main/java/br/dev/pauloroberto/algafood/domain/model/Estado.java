package br.dev.pauloroberto.algafood.domain.model;

import br.dev.pauloroberto.algafood.core.validation.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @NotNull(groups = Groups.EstadoId.class)
    private Long id;
    @Column(nullable = false)
    @NotBlank
    private String nome;

    public void setEstado(EstadosEnum estado) {
        this.nome = estado.toString();
    }
}
