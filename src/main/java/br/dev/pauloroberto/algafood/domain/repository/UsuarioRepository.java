package br.dev.pauloroberto.algafood.domain.repository;

import br.dev.pauloroberto.algafood.domain.model.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {
}
