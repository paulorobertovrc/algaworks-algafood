package br.dev.pauloroberto.algafood.api.openapi.model;

import br.dev.pauloroberto.algafood.api.model.UsuarioDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@ApiModel("UsuariosModel")
@Data
public class UsuariosModelOpenApi {

    private UsuariosEmbeddedModelOpenApi _embedded;
    private LinksModelOpenApi _links;

    @ApiModel("UsuariosEmbeddedModel")
    @Data
    public static class UsuariosEmbeddedModelOpenApi {

        private List<UsuarioDto> usuarios;

    }

}
