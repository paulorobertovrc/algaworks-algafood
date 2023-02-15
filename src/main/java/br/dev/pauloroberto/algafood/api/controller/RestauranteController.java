package br.dev.pauloroberto.algafood.api.controller;

import br.dev.pauloroberto.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.dev.pauloroberto.algafood.domain.model.Restaurante;
import br.dev.pauloroberto.algafood.domain.repository.RestauranteRepository;
import br.dev.pauloroberto.algafood.domain.service.CadastroRestauranteService;
import br.dev.pauloroberto.algafood.infrastructure.repository.spec.RestauranteComFreteGratisSpec;
import br.dev.pauloroberto.algafood.infrastructure.repository.spec.RestauranteComNomeSemelhanteSpec;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @GetMapping
    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long id) {
        Optional<Restaurante> restaurante = restauranteRepository.findById(id);

        return restaurante.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()
                );

    }

    @GetMapping("/por-taxa-frete")
    public List<Restaurante> buscarPorTaxaFrete(@RequestParam BigDecimal taxaInicial, @RequestParam BigDecimal taxaFinal) {
        return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
    }

    @GetMapping("/por-nome")
    public List<Restaurante> buscarPorNome(@RequestParam String nome, Long cozinhaId) {
        return restauranteRepository.consultarPorNome(nome, cozinhaId);
    }

    @GetMapping("/primeiro-por-nome")
    public Optional<Restaurante> buscarPrimeiroPorNome(@RequestParam String nome) {
        return restauranteRepository.findFirstRestauranteByNomeContaining(nome);
    }

    @GetMapping("/top2-por-nome")
    public List<Restaurante> buscarTop2PorNome(@RequestParam String nome) {
        return restauranteRepository.findTop2ByNomeContaining(nome);
    }

    @GetMapping("/contagem-por-cozinha")
    public int buscarContagemPorCozinha(@RequestParam Long cozinhaId) {
        return restauranteRepository.countByCozinhaId(cozinhaId);
    }

    @GetMapping("/busca-customizada")
    public List<Restaurante> buscarCustomizada(String nome,
                                               BigDecimal taxaFreteInicial,
                                               BigDecimal taxaFreteFinal) {
        return restauranteRepository.buscaCustomizada(nome, taxaFreteInicial, taxaFreteFinal);
    }

    @GetMapping("/com-frete-gratis")
    public List<Restaurante> restaurantesComFreteGratis(String nome) {
        var comFreteGratis = new RestauranteComFreteGratisSpec();
        var comNomeSemelhante = new RestauranteComNomeSemelhanteSpec(nome);

        return restauranteRepository.findAll(comFreteGratis.and(comNomeSemelhante));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
        try {
            restaurante = cadastroRestauranteService.salvar(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(restaurante);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Restaurante restauranteAtualizado) {
        try {
            Optional<Restaurante> restaurante = restauranteRepository.findById(id);
            if (restaurante.isPresent()) {
                restauranteAtualizado.setId(restaurante.get().getId());
                restauranteAtualizado = cadastroRestauranteService.salvar(restauranteAtualizado);
                return ResponseEntity.ok(restauranteAtualizado);
            }
            return ResponseEntity.notFound().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
        Optional<Restaurante> restaurante = restauranteRepository.findById(id);

        if (restaurante.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        merge(campos, restaurante.get());

        return atualizar(id, restaurante.get());
    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

        dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            Objects.requireNonNull(field).setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

            ReflectionUtils.setField(field, restauranteDestino, novoValor);
        });
    }
}
