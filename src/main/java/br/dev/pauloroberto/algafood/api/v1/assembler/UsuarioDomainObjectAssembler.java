package br.dev.pauloroberto.algafood.api.v1.assembler;

import br.dev.pauloroberto.algafood.api.v1.model.input.UsuarioInputDto;
import br.dev.pauloroberto.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioDomainObjectAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public Usuario toDomainObject(UsuarioInputDto usuarioInput) {
        return modelMapper.map(usuarioInput, Usuario.class);
    }

    public void copyToDomainObject(UsuarioInputDto usuarioInput, Usuario usuario) {
        modelMapper.map(usuarioInput, usuario);
    }

}
