package br.dev.pauloroberto.algafood.domain.model;

import br.dev.pauloroberto.algafood.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String nome;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull
    @Valid
    @ConvertGroup(to = Groups.EstadoId.class)
    private Estado estado;
}
