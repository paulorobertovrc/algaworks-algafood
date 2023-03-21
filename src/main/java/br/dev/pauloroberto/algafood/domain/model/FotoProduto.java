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
public class FotoProduto {
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "produto_id")
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Produto produto;
    private String nomeArquivo;
    private String descricao;
    private String contentType;
    private Long tamanho;
}
