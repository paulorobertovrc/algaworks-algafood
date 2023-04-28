package br.dev.pauloroberto.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoDto {
    @ApiModelProperty(example = "11000-000")
    private String cep;

    @ApiModelProperty(example = "Rua das Laranjeiras")
    private String logradouro;

    @ApiModelProperty(example = "1000")
    private String numero;

    @ApiModelProperty(example = "Apto 101")
    private String complemento;

    @ApiModelProperty(example = "Centro")
    private String bairro;

    private CidadeResumoDto cidade;


//    private String nomeCidade;
//    private String nomeCidadeNomeEstado; // Alternativamente, poderia ser feita uma configuração na classe ModelMapperConfig,
//    // para mapear o atributo nome do objeto Estado.
//
//
//    // Este atributo não existe na classe Endereco, mas foi criado para facilitar a exibição do nome da cidade e do estado.
//    // Isso é feito com o auxílio da biblioteca ModelMapper, que faz o mapeamento entre os atributos de um objeto e os
//    // atributos de outro objeto.
//    @JsonIgnore
//    private String nomeCidadeEstado;
//    public String getNomeCidadeEstado() {
//        return nomeCidade + " / " + nomeCidadeEstado;
//    }

}
