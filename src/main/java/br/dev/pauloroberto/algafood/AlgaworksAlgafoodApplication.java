package br.dev.pauloroberto.algafood;

import br.dev.pauloroberto.algafood.infrastructure.repository.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class AlgaworksAlgafoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlgaworksAlgafoodApplication.class, args);
	}

}
