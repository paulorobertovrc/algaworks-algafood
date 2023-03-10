package br.dev.pauloroberto.algafood.api.controller;

import br.dev.pauloroberto.algafood.api.assembler.RestauranteDomainObjectAssembler;
import br.dev.pauloroberto.algafood.api.assembler.RestauranteDtoAssembler;
import br.dev.pauloroberto.algafood.api.model.RestauranteDto;
import br.dev.pauloroberto.algafood.api.model.input.RestauranteInputDto;
import br.dev.pauloroberto.algafood.api.model.view.RestauranteView;
import br.dev.pauloroberto.algafood.domain.exception.*;
import br.dev.pauloroberto.algafood.domain.model.Restaurante;
import br.dev.pauloroberto.algafood.domain.repository.RestauranteRepository;
import br.dev.pauloroberto.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJacksonValue;
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
@RequestMapping("/restaurantes")
public class RestauranteController {
    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;
    @Autowired
    private RestauranteDtoAssembler restauranteDtoAssembler;
    @Autowired
    private RestauranteDomainObjectAssembler restauranteDomainObjectAssembler;

//    @GetMapping
//    public List<RestauranteDto> pesquisar() {
//        return restauranteDtoAssembler.toDtoList(restauranteRepository.findAll());
//    }

//    @GetMapping(params = "projecao=resumo")
//    @JsonView(RestauranteView.Resumo.class)
//    public List<RestauranteDto> listarResumido() {
//        return restauranteDtoAssembler.toDtoList(restauranteRepository.findAll());
//    }

    @GetMapping
    public MappingJacksonValue listar(@RequestParam(required = false) String projecao) {
        List<Restaurante> restaurantes = restauranteRepository.findAll();
        List<RestauranteDto> restaurantesDto = restauranteDtoAssembler.toDtoList(restaurantes);

        MappingJacksonValue restaurantesWrapper = new MappingJacksonValue(restaurantesDto);

        restaurantesWrapper.setSerializationView(RestauranteView.Resumo.class);

        if ("apenas-nome".equals(projecao)) {
            restaurantesWrapper.setSerializationView(RestauranteView.ApenasNome.class);
        } else if ("completo".equals(projecao)) {
            restaurantesWrapper.setSerializationView(null);
        }

        return restaurantesWrapper;
    }

    @GetMapping("/{id}")
    public RestauranteDto buscar(@PathVariable Long id) {
        Restaurante restaurante = cadastroRestauranteService.verificarSeExiste(id);

        return restauranteDtoAssembler.toDto(restaurante);
    }

    @GetMapping("/por-taxa-frete")
    public List<RestauranteDto> buscarPorTaxaFrete(@RequestParam BigDecimal taxaInicial,
                                                   @RequestParam BigDecimal taxaFinal) {
        return restauranteDtoAssembler.toDtoList(restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal));
    }

    @GetMapping("/por-nome")
    public List<RestauranteDto> buscarPorNome(@RequestParam String nome,
                                              Long cozinhaId) {
        return restauranteDtoAssembler.toDtoList(restauranteRepository.consultarPorNome(nome, cozinhaId));
    }

    @GetMapping("/primeiro-por-nome")
    public Optional<RestauranteDto> buscarPrimeiroPorNome(@RequestParam String nome) {
        return Optional.of(restauranteDtoAssembler.toDto(restauranteRepository.findFirstRestauranteByNomeContaining(nome)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(
                        String.format("N??o existe um restaurante com o nome %s", nome)
                )))
        );
    }

    @GetMapping("/top2-por-nome")
    public List<RestauranteDto> buscarTop2PorNome(@RequestParam String nome) {
        return restauranteDtoAssembler.toDtoList(restauranteRepository.findTop2ByNomeContaining(nome));
    }

    @GetMapping("/contagem-por-cozinha")
    public int buscarContagemPorCozinha(@RequestParam Long cozinhaId) {
        return restauranteRepository.countByCozinhaId(cozinhaId);
    }

    @GetMapping("/busca-customizada")
    public List<RestauranteDto> buscarCustomizada(String nome,
                                               BigDecimal taxaFreteInicial,
                                               BigDecimal taxaFreteFinal) {
        return restauranteDtoAssembler.toDtoList(restauranteRepository.buscaCustomizada(nome, taxaFreteInicial, taxaFreteFinal));
    }

    @GetMapping("/com-frete-gratis")
    public List<RestauranteDto> restaurantesComFreteGratis(@RequestParam(required = false) String nome) {
        return restauranteDtoAssembler.toDtoList(restauranteRepository.findComFreteGratis(nome));
    }

    @GetMapping("/primeiro")
    public Optional<RestauranteDto> buscarPrimeiro() {
        return Optional.of(restauranteDtoAssembler.toDto(restauranteRepository.buscarPrimeiro()
                .orElseThrow(() -> new RestauranteNaoEncontradoException("N??o existe um restaurante cadastrado"))
        ));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDto adicionar(@RequestBody @Valid RestauranteInputDto restauranteInput) {
        try {
            Restaurante restaurante = restauranteDomainObjectAssembler.toDomainObject(restauranteInput);

            return restauranteDtoAssembler.toDto(cadastroRestauranteService.salvar(restaurante));
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

            return restauranteDtoAssembler.toDto(cadastroRestauranteService.salvar(restauranteAtual));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long id) {
        cadastroRestauranteService.ativar(id);
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
    public void abrir(@PathVariable Long id) {
        cadastroRestauranteService.abrir(id);
    }

    @PutMapping("/{id}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechar(@PathVariable Long id) {
        cadastroRestauranteService.fechar(id);
    }

    @DeleteMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long id) {
        cadastroRestauranteService.inativar(id);
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
