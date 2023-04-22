package br.dev.pauloroberto.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoDto {
    @ApiModelProperty(value = "Nome do arquivo da foto do produto", example = "cebola_roxa.jpg")
    private String nomeArquivo;

    @ApiModelProperty(value = "Descrição da foto do produto", example = "Foto do prato de cebola roxa")
    private String descricao;

    @ApiModelProperty(value = "Tipo de arquivo da foto do produto", example = "image/jpeg")
    private String contentType;

    @ApiModelProperty(value = "Tamanho da foto do produto em bytes", example = "956412")
    private Long tamanho;
}
