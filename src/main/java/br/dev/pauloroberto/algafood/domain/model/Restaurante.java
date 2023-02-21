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
import javax.validation.groups.ConvertGroup;
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
    @NotBlank
    private String nome;
    @Column(name = "taxa_frete", nullable = false)
    @PositiveOrZero
    @NotNull
    private BigDecimal taxaFrete;
    @ManyToOne
    @JoinColumn(name = "cozinha_id", nullable = false)
    @NotNull
    @Valid // @Valid para validar as propriedades do cozinha e não apenas o objeto cozinha
    @ConvertGroup(to = Groups.CozinhaId.class) // @ConvertGroup para converter o grupo de validação padrão para o grupo de validação de cadastro de restaurante
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
