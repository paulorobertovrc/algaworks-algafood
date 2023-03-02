package br.dev.pauloroberto.algafood.api.assembler;

import br.dev.pauloroberto.algafood.api.model.input.RestauranteInputDto;
import br.dev.pauloroberto.algafood.domain.model.Cidade;
import br.dev.pauloroberto.algafood.domain.model.Cozinha;
import br.dev.pauloroberto.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteDomainObjectAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public Restaurante toDomainObject(RestauranteInputDto restauranteInput) {
        return modelMapper.map(restauranteInput, Restaurante.class);
    }

    public void copyToDomainObject(RestauranteInputDto restauranteInput, Restaurante restaurante) {
        // É instanciada uma cozinha nova para evitar o seguinte erro:
        // org.hibernate.HibernateException:
        // identifier of an instance of br.dev.pauloroberto.algafood.domain.model.Cozinha was altered from 1 to 2
        restaurante.setCozinha(new Cozinha());

        // É instanciada uma cidade nova para evitar o mesmo erro acima
        if (restaurante.getEndereco() != null) {
            restaurante.getEndereco().setCidade(new Cidade());
        }

        modelMapper.map(restauranteInput, restaurante);
    }

}
