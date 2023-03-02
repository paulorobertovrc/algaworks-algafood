package br.dev.pauloroberto.algafood.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoDto {
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String nomeCidade;
    private String nomeCidadeNomeEstado; // Alternativamente, poderia ser feita uma configuração na classe ModelMapperConfig,
    // para mapear o atributo nome do objeto Estado.


    // Este atributo não existe na classe Endereco, mas foi criado para facilitar a exibição do nome da cidade e do estado.
    // Isso é feito com o auxílio da biblioteca ModelMapper, que faz o mapeamento entre os atributos de um objeto e os
    // atributos de outro objeto.
    @JsonIgnore
    private String nomeCidadeEstado;
    public String getNomeCidadeEstado() {
        return nomeCidade + " / " + nomeCidadeEstado;
    }

}
