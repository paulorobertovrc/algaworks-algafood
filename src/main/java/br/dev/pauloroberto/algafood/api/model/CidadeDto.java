package br.dev.pauloroberto.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
//@ApiModel(value = "Cidade", description = "Representa uma cidade") // Define o nome que será exibido na aba "Model" ou "Schemas" do Swagger
public class CidadeDto {
    @ApiModelProperty(value = "ID da cidade", example = "1", required = true)
    private Long id;
    @ApiModelProperty(value = "Nome da cidade", example = "São Paulo", required = true)
    private String nome;
    private EstadoDto estado;
}
