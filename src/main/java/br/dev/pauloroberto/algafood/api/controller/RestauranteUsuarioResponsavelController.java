package br.dev.pauloroberto.algafood.api.controller;

import br.dev.pauloroberto.algafood.api.assembler.UsuarioDtoAssembler;
import br.dev.pauloroberto.algafood.api.model.UsuarioDto;
import br.dev.pauloroberto.algafood.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import br.dev.pauloroberto.algafood.domain.model.Restaurante;
import br.dev.pauloroberto.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
//@ApiIgnore // Não é necessária após a criação do RestauranteUsuarioResponsavelControllerOpenApi
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {
    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;
    @Autowired
    private UsuarioDtoAssembler usuarioDtoAssembler;

    @GetMapping
    public List<UsuarioDto> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestauranteService.verificarSeExiste(restauranteId);

        return usuarioDtoAssembler.toDtoList(restaurante.getResponsaveis());
    }

    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestauranteService.associarResponsavel(restauranteId, usuarioId);
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestauranteService.desassociarResponsavel(restauranteId, usuarioId);
    }

}
