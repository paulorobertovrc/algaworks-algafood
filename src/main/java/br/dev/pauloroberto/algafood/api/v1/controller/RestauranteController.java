package br.dev.pauloroberto.algafood.api.v1.controller;

import br.dev.pauloroberto.algafood.api.v1.assembler.RestauranteApenasNomeDtoAssembler;
import br.dev.pauloroberto.algafood.api.v1.assembler.RestauranteBasicoDtoAssembler;
import br.dev.pauloroberto.algafood.api.v1.assembler.RestauranteDomainObjectAssembler;
import br.dev.pauloroberto.algafood.api.v1.assembler.RestauranteDtoAssembler;
import br.dev.pauloroberto.algafood.api.v1.model.RestauranteApenasNomeDto;
import br.dev.pauloroberto.algafood.api.v1.model.RestauranteDto;
import br.dev.pauloroberto.algafood.api.v1.model.input.RestauranteInputDto;
import br.dev.pauloroberto.algafood.api.v1.openapi.controller.RestauranteControllerOpenApi;
import br.dev.pauloroberto.algafood.domain.exception.CidadeNaoEncontradaException;
import br.dev.pauloroberto.algafood.domain.exception.CozinhaNaoEncontradaException;
import br.dev.pauloroberto.algafood.domain.exception.NegocioException;
import br.dev.pauloroberto.algafood.domain.exception.RestauranteNaoEncontradoException;
import br.dev.pauloroberto.algafood.domain.model.Restaurante;
import br.dev.pauloroberto.algafood.domain.repository.RestauranteRepository;
import br.dev.pauloroberto.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(value = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi {
    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;
    @Autowired
    private RestauranteDtoAssembler restauranteDtoAssembler;
    @Autowired
    private RestauranteDomainObjectAssembler restauranteDomainObjectAssembler;
    @Autowired
    private RestauranteBasicoDtoAssembler restauranteBasicoDtoAssembler;
    @Autowired
    private RestauranteApenasNomeDtoAssembler restauranteApenasNomeDtoAssembler;

//    @GetMapping
//    public List<RestauranteDto> pesquisar() {
//        return restauranteDtoAssembler.toDtoList(restauranteRepository.findAll());
//    }

//    @GetMapping(params = "projecao=resumo")
//    @JsonView(RestauranteView.Resumo.class)
//    public List<RestauranteDto> listarResumido() {
//        return restauranteDtoAssembler.toDtoList(restauranteRepository.findAll());
//    }

    @Override
    @GetMapping
    public CollectionModel<RestauranteDto> listar() {
        return restauranteDtoAssembler.toCollectionModel(restauranteRepository.findAll());
    }

    @Override
    @GetMapping(params = "projecao=apenas-nome")
    public CollectionModel<RestauranteApenasNomeDto> listarApenasNomes() {
        return restauranteApenasNomeDtoAssembler.toCollectionModel(restauranteRepository.findAll());
    }

    @GetMapping("/{id}")
    public RestauranteDto buscar(@PathVariable Long id) {
        Restaurante restaurante = cadastroRestauranteService.verificarSeExiste(id);

        return restauranteDtoAssembler.toModel(restaurante);
    }

    @GetMapping("/por-taxa-frete")
    public CollectionModel<RestauranteDto> buscarPorTaxaFrete(@RequestParam BigDecimal taxaInicial,
                                                              @RequestParam BigDecimal taxaFinal) {
        return restauranteDtoAssembler.toCollectionModel(
                restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal));
    }

    @GetMapping("/por-nome")
    public @NotNull CollectionModel<RestauranteDto> buscarPorNome(@RequestParam String nome,
                                                                  Long cozinhaId) {
        return restauranteDtoAssembler.toCollectionModel(restauranteRepository.consultarPorNome(nome, cozinhaId));
    }

    @GetMapping("/primeiro-por-nome")
    public Optional<RestauranteDto> buscarPrimeiroPorNome(@RequestParam String nome) {
        return Optional.of(
                restauranteDtoAssembler.toModel(restauranteRepository.findFirstRestauranteByNomeContaining(nome)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(
                        String.format("Não existe um restaurante com o nome %s", nome)
                )))
        );
    }

    @GetMapping("/top2-por-nome")
    public @NotNull CollectionModel<RestauranteDto> buscarTop2PorNome(@RequestParam String nome) {
        return restauranteDtoAssembler.toCollectionModel(restauranteRepository.findTop2ByNomeContaining(nome));
    }

    @GetMapping("/contagem-por-cozinha")
    public int buscarContagemPorCozinha(@RequestParam Long cozinhaId) {
        return restauranteRepository.countByCozinhaId(cozinhaId);
    }

    @GetMapping("/busca-customizada")
    public @NotNull CollectionModel<RestauranteDto> buscarCustomizada(String nome,
                                                                      BigDecimal taxaFreteInicial,
                                                                      BigDecimal taxaFreteFinal) {
        return restauranteDtoAssembler.toCollectionModel(
                restauranteRepository.buscaCustomizada(nome, taxaFreteInicial, taxaFreteFinal));
    }

    @GetMapping("/com-frete-gratis")
    public @NotNull CollectionModel<RestauranteDto> restaurantesComFreteGratis(
            @RequestParam(required = false) String nome) {
        return restauranteDtoAssembler.toCollectionModel(restauranteRepository.findComFreteGratis(nome));
    }

    @GetMapping("/primeiro")
    public Optional<RestauranteDto> buscarPrimeiro() {
        return Optional.of(restauranteDtoAssembler.toModel(restauranteRepository.buscarPrimeiro()
                .orElseThrow(() -> new RestauranteNaoEncontradoException("Não existe um restaurante cadastrado"))
        ));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDto adicionar(@RequestBody @Valid RestauranteInputDto restauranteInput) {
        try {
            Restaurante restaurante = restauranteDomainObjectAssembler.toDomainObject(restauranteInput);

            return restauranteDtoAssembler.toModel(cadastroRestauranteService.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public RestauranteDto atualizar(@PathVariable Long id,
                                    @RequestBody @Valid RestauranteInputDto restauranteInput) {
        try {
            Restaurante restauranteAtual = cadastroRestauranteService.verificarSeExiste(id);

            restauranteDomainObjectAssembler.copyToDomainObject(restauranteInput, restauranteAtual);

            return restauranteDtoAssembler.toModel(cadastroRestauranteService.salvar(restauranteAtual));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        cadastroRestauranteService.ativar(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        cadastroRestauranteService.inativar(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> ids) {
        try {
            cadastroRestauranteService.ativar(ids);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiplos(@RequestBody List<Long> ids) {
        try {
            cadastroRestauranteService.inativar(ids);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> abrir(@PathVariable Long id) {
        cadastroRestauranteService.abrir(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> fechar(@PathVariable Long id) {
        cadastroRestauranteService.fechar(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public RestauranteDto atualizarParcial(@PathVariable Long id,
                                           @RequestBody Map<String, Object> campos,
                                           HttpServletRequest request) {
        Restaurante restaurante = cadastroRestauranteService.verificarSeExiste(id);

        merge(campos, restaurante, request);

        return atualizar(id, restauranteDtoAssembler.toInputDto(restaurante));
    }

    private void merge(Map<String, Object> dadosOrigem,
                       Restaurante restauranteDestino,
                       HttpServletRequest request) {
        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

            dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
                Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
                Objects.requireNonNull(field).setAccessible(true);

                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

                ReflectionUtils.setField(field, restauranteDestino, novoValor);
            });
        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }
    }

}
