package br.dev.pauloroberto.algafood;

import br.dev.pauloroberto.algafood.domain.exception.CozinhaNaoEncontradaException;
import br.dev.pauloroberto.algafood.domain.exception.NegocioException;
import br.dev.pauloroberto.algafood.domain.model.Cozinha;
import br.dev.pauloroberto.algafood.domain.service.CadastroCozinhaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class CadastroCozinhaIT {

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	@Test
	void testarCadastroCozinhaComSucesso() {
		// cenário
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("Chinesa");

		// ação
		cadastroCozinhaService.salvar(cozinha);

		// validação
		assertThat(cozinha).isNotNull();
		assertThat(cozinha.getId()).isNotNull();
		assertThat(cozinha.getNome()).isEqualTo("Chinesa");
	}

	@Test
	void testarCadastroCozinhaSemNome() {
		Cozinha cozinha = new Cozinha();
		cozinha.setNome(null);

		Throwable erro = Assertions.assertThrows(ConstraintViolationException.class, () -> {
			cadastroCozinhaService.salvar(cozinha);
		});

		assertThat(erro).isNotNull();
	}

	@Test
	void testarCadastroCozinhaComNomeDuplicado() {
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("Brasileira");

		Throwable erro = Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
			cadastroCozinhaService.salvar(cozinha);
		});

		assertThat(erro).isNotNull();
	}

	@Test
	void testarExclusaoCozinhaComSucesso() {
		// cenário
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("Chinesa");
		cadastroCozinhaService.salvar(cozinha);
		cadastroCozinhaService.excluir(cozinha.getId());

		// ação
		Throwable erro = Assertions.assertThrows(CozinhaNaoEncontradaException.class, () -> {
			cadastroCozinhaService.verificarSeExiste(cozinha.getId());
		});

		// validação
		assertThat(erro).isNotNull();
	}

	@Test
	void testarExclusaoCozinhaEmUso() {
		// cenário
		Cozinha cozinha = cadastroCozinhaService.verificarSeExiste(1L);

		// ação
		Throwable erro = Assertions.assertThrows(NegocioException.class, () -> {
			cadastroCozinhaService.excluir(cozinha.getId());
		});

		// validação
		assertThat(erro).isNotNull();
		assertThat(erro.getMessage()).isEqualTo("A Cozinha de código %d não pode ser removida porque está em uso",
				cozinha.getId());
	}

	@Test
	void testarExclusaoCozinhaInexistente() {
		// cenário
		Long idCozinhaInexistente = 100L;

		// ação
		Throwable erro = Assertions.assertThrows(CozinhaNaoEncontradaException.class, () -> {
			cadastroCozinhaService.excluir(idCozinhaInexistente);
		});

		// validação
		assertThat(erro).isNotNull();
		assertThat(erro.getMessage()).isEqualTo("Não existe um cadastro de cozinha com código %d", idCozinhaInexistente);
	}

}
