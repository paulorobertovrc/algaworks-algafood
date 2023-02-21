package br.dev.pauloroberto.algafood.domain.model;

import br.dev.pauloroberto.algafood.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(nullable = false)
    @NotBlank(groups = Groups.CadastroRestaurante.class)
    private String nome;
    @Column(name = "taxa_frete", nullable = false)
    @PositiveOrZero
    @NotNull(groups = Groups.CadastroRestaurante.class)
    private BigDecimal taxaFrete;
    @ManyToOne
    @JoinColumn(name = "cozinha_id", nullable = false)
    @NotNull(groups = Groups.CadastroRestaurante.class)
    @Valid // @Valid para validar as propriedades do cozinha e não apenas o objeto cozinha
    private Cozinha cozinha;
    @JsonIgnore
    @Embedded
    private Endereco endereco;
    @JsonIgnore
    @Column(nullable = false, columnDefinition = "datetime")
    @CreationTimestamp
    private LocalDateTime dataCadastro;
    @JsonIgnore
    @Column(nullable = false, columnDefinition = "datetime")
    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private List<FormaPagamento> formasPagamento = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos = new ArrayList<>();
}
