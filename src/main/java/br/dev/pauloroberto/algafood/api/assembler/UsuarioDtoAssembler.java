package br.dev.pauloroberto.algafood.api.assembler;

import br.dev.pauloroberto.algafood.api.model.UsuarioDto;
import br.dev.pauloroberto.algafood.api.model.input.UsuarioInputDto;
import br.dev.pauloroberto.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioDtoAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public UsuarioDto toDto(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioDto.class);
    }

    public List<UsuarioDto> toDtoList(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(this::toDto)
                .toList();
    }

    public UsuarioInputDto toInputDto(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioInputDto.class);
    }
}
