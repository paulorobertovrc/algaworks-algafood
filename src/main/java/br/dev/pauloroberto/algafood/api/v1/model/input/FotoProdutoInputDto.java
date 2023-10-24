package br.dev.pauloroberto.algafood.api.v1.model.input;

import br.dev.pauloroberto.algafood.core.validation.FileContentType;
import br.dev.pauloroberto.algafood.core.validation.FileSize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FotoProdutoInputDto {
    @NotBlank
    @ApiModelProperty(value = "Descrição da foto do produto", required = true)
    private String descricao;

    @NotNull
    @FileSize(max = "500KB")
    @FileContentType(allowed = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ApiModelProperty(hidden = true)
    private MultipartFile arquivo;
}
