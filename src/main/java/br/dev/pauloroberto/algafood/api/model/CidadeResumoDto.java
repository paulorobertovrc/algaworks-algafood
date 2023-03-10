package br.dev.pauloroberto.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeResumoDto {

    // Esta classe é uma representação simplificada de Cidade, apenas com os atributos que serão exibidos na resposta da API.
    // Deve ser utilizada na classe EstadoDto, sendo que nesse caso o atributo cidade deve ser do tipo CidadeResumoDto.
    // Isso criaria, na resposta da API, um objeto CidadeResumoDto dentro do EnderecoDto, que por sua vez está dentro
    // do RestauranteDto. Dentro do objeto CidadeResumoDto, teríamos ainda o atributo nomeEstado, que é o nome do estado
    // ou um objeto EstadoResumoDto.

    private Long id;
    private String nome;
    private String nomeEstado;
}
