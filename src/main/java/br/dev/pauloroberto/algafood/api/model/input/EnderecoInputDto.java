package br.dev.pauloroberto.algafood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EnderecoInputDto {
    @NotBlank
    @ApiModelProperty(example = "11000-000", required = true)
    private String cep;

    @NotBlank
    @ApiModelProperty(example = "Rua das Flores", required = true)
    private String logradouro;

    @NotBlank
    @ApiModelProperty(example = "100", required = true)
    private String numero;

    @ApiModelProperty(example = "Bloco 1, Apto 101")
    private String complemento;

    @NotBlank
    @ApiModelProperty(example = "Centro", required = true)
    private String bairro;

    @NotNull
    @Valid
    private CidadeIdInputDto cidade;

}
