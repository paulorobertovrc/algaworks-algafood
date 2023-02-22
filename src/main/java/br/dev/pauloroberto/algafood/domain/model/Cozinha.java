package br.dev.pauloroberto.algafood.domain.model;

import br.dev.pauloroberto.algafood.core.validation.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "nome", name = "UK_nome_cozinha_unico") })
public class Cozinha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @NotNull(groups = Groups.CozinhaId.class)
    private Long id;
    @Column(nullable = false)
    @NotBlank
    private String nome;

    @JsonIgnore // Ignora a propriedade no retorno da API e evita o loop infinito
    @OneToMany(mappedBy = "cozinha")
    private List<Restaurante> restaurantes = new ArrayList<>();
}
