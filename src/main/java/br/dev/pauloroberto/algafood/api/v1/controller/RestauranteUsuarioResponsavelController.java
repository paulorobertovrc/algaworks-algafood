package br.dev.pauloroberto.algafood.api.v1.controller;

import br.dev.pauloroberto.algafood.api.v1.assembler.UsuarioDtoAssembler;
import br.dev.pauloroberto.algafood.api.v1.helper.LinkHelper;
import br.dev.pauloroberto.algafood.api.v1.model.UsuarioDto;
import br.dev.pauloroberto.algafood.api.v1.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import br.dev.pauloroberto.algafood.domain.model.Restaurante;
import br.dev.pauloroberto.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
//@ApiIgnore // Não é necessária após a criação do RestauranteUsuarioResponsavelControllerOpenApi
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {
    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;
    @Autowired
    private UsuarioDtoAssembler usuarioDtoAssembler;
    @Autowired
    private LinkHelper linkHelper;

    @Override
    @GetMapping
    public CollectionModel<UsuarioDto> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestauranteService.verificarSeExiste(restauranteId);

        CollectionModel<UsuarioDto> usuariosDto = usuarioDtoAssembler.toCollectionModel(restaurante.getResponsaveis())
                .removeLinks()
                .add(linkHelper.linkToResponsaveisRestaurante(restauranteId))
                .add(linkHelper.linkToRestauranteResponsavelAssociacao(restauranteId, "associar"));

        usuariosDto.getContent().forEach(usuarioDto ->
                usuarioDto.add(linkHelper.linkToRestauranteResponsavelDesassociacao(
                        restauranteId, usuarioDto.getId(), "desassociar")));

        return usuariosDto;
    }

    @Override
    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestauranteService.associarResponsavel(restauranteId, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestauranteService.desassociarResponsavel(restauranteId, usuarioId);
        return ResponseEntity.noContent().build();
    }

}
