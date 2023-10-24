package br.dev.pauloroberto.algafood.api.v1.assembler;

import br.dev.pauloroberto.algafood.api.v1.model.input.GrupoInputDto;
import br.dev.pauloroberto.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrupoDomainObjectAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public Grupo toDomainObject(GrupoInputDto grupoInput) {
        return modelMapper.map(grupoInput, Grupo.class);
    }

    public void copyToDomainObject(GrupoInputDto grupoInput, Grupo grupo) {
        modelMapper.map(grupoInput, grupo);
    }

}
