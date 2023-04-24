package br.dev.pauloroberto.algafood.api.assembler;

import br.dev.pauloroberto.algafood.api.controller.UsuarioController;
import br.dev.pauloroberto.algafood.api.controller.UsuarioGrupoController;
import br.dev.pauloroberto.algafood.api.model.UsuarioDto;
import br.dev.pauloroberto.algafood.domain.model.Usuario;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioDtoAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioDto> {
    @Autowired
    private ModelMapper modelMapper;

    public UsuarioDtoAssembler() {
        super(UsuarioController.class, UsuarioDto.class);
    }

    @Override
    public @NotNull UsuarioDto toModel(@NotNull Usuario usuario) {
        UsuarioDto usuarioDto = modelMapper.map(usuario, UsuarioDto.class);

        usuarioDto.add(linkTo(methodOn(UsuarioController.class).buscar(usuarioDto.getId())).withSelfRel());
        usuarioDto.add(linkTo(methodOn(UsuarioController.class).listar()).withRel("usuarios"));
        usuarioDto.add(linkTo(methodOn(UsuarioGrupoController.class).listar(usuarioDto.getId()))
                .withRel("grupos-usuario"));

        return usuarioDto;
    }

    @Override
    public @NotNull CollectionModel<UsuarioDto> toCollectionModel(@NotNull Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(methodOn(UsuarioController.class).listar()).withSelfRel());
    }

}
