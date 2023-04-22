package br.dev.pauloroberto.algafood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioInputDto {
    @NotBlank
    @ApiModelProperty(example = "Paulo Roberto", required = true)
    private String nome;

    @NotBlank
    @Email
    @ApiModelProperty(example = "paulo@email.com", required = true)
    private String email;
}
