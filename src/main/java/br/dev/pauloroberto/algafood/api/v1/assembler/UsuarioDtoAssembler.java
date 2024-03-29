package br.dev.pauloroberto.algafood.api.v1.assembler;

import br.dev.pauloroberto.algafood.api.v1.controller.UsuarioController;
import br.dev.pauloroberto.algafood.api.v1.helper.LinkHelper;
import br.dev.pauloroberto.algafood.api.v1.model.UsuarioDto;
import br.dev.pauloroberto.algafood.domain.model.Usuario;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class UsuarioDtoAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioDto> {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LinkHelper linkHelper;

    public UsuarioDtoAssembler() {
        super(UsuarioController.class, UsuarioDto.class);
    }

    @Override
    public @NotNull UsuarioDto toModel(@NotNull Usuario usuario) {
        UsuarioDto usuarioDto = modelMapper.map(usuario, UsuarioDto.class);

        usuarioDto.add(linkHelper.linkToUsuario(usuarioDto.getId()));
        usuarioDto.add(linkHelper.linkToUsuarios("usuarios"));
        usuarioDto.add(linkHelper.linkToGruposUsuario(usuarioDto.getId(), "grupos-usuario"));

        return usuarioDto;
    }

    @Override
    public @NotNull CollectionModel<UsuarioDto> toCollectionModel(@NotNull Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities)
                .add(linkHelper.linkToUsuarios());
    }

}
