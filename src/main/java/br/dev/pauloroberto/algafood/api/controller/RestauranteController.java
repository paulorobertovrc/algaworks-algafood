package br.dev.pauloroberto.algafood.api.controller;

import br.dev.pauloroberto.algafood.api.model.CozinhaDto;
import br.dev.pauloroberto.algafood.api.model.RestauranteDto;
import br.dev.pauloroberto.algafood.api.model.input.CozinhaIdInputDto;
import br.dev.pauloroberto.algafood.api.model.input.RestauranteInputDto;
import br.dev.pauloroberto.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.dev.pauloroberto.algafood.domain.exception.NegocioException;
import br.dev.pauloroberto.algafood.domain.exception.RestauranteNaoEncontradoException;
import br.dev.pauloroberto.algafood.domain.model.Cozinha;
import br.dev.pauloroberto.algafood.domain.model.Restaurante;
import br.dev.pauloroberto.algafood.domain.repository.RestauranteRepository;
import br.dev.pauloroberto.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/restaurantes")
public class RestauranteController {
    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @GetMapping
    public List<RestauranteDto> listar() {
        return toDtoList(restauranteRepository.findAll());
    }

    @GetMapping("/{id}")
    public RestauranteDto buscar(@PathVariable Long id) {
        Restaurante restaurante = cadastroRestauranteService.verificarSeExiste(id);

        return toDto(restaurante);
    }

    @GetMapping("/por-taxa-frete")
    public List<RestauranteDto> buscarPorTaxaFrete(@RequestParam BigDecimal taxaInicial,
                                                   @RequestParam BigDecimal taxaFinal) {
        return toDtoList(restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal));
    }

    @GetMapping("/por-nome")
    public List<RestauranteDto> buscarPorNome(@RequestParam String nome, Long cozinhaId) {
        return toDtoList(restauranteRepository.consultarPorNome(nome, cozinhaId));
    }

    @GetMapping("/primeiro-por-nome")
    public Optional<RestauranteDto> buscarPrimeiroPorNome(@RequestParam String nome) {
        return Optional.of(toDto(restauranteRepository.findFirstRestauranteByNomeContaining(nome)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(
                        String.format("Não existe um restaurante com o nome %s", nome)
                )))
        );
    }

    @GetMapping("/top2-por-nome")
    public List<RestauranteDto> buscarTop2PorNome(@RequestParam String nome) {
        return toDtoList(restauranteRepository.findTop2ByNomeContaining(nome));
    }

    @GetMapping("/contagem-por-cozinha")
    public int buscarContagemPorCozinha(@RequestParam Long cozinhaId) {
        return restauranteRepository.countByCozinhaId(cozinhaId);
    }

    @GetMapping("/busca-customizada")
    public List<RestauranteDto> buscarCustomizada(String nome,
                                               BigDecimal taxaFreteInicial,
                                               BigDecimal taxaFreteFinal) {
        return toDtoList(restauranteRepository.buscaCustomizada(nome, taxaFreteInicial, taxaFreteFinal));
    }

    @GetMapping("/com-frete-gratis")
    public List<RestauranteDto> restaurantesComFreteGratis(@RequestParam(required = false) String nome) {
        return toDtoList(restauranteRepository.findComFreteGratis(nome));
    }

    @GetMapping("/primeiro")
    public Optional<RestauranteDto> buscarPrimeiro() {
        return Optional.of(toDto(restauranteRepository.buscarPrimeiro()
                .orElseThrow(() -> new RestauranteNaoEncontradoException("Não existe um restaurante cadastrado"))
        ));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDto adicionar(@RequestBody @Valid RestauranteInputDto restauranteInput) {
        try {
            Restaurante restaurante = toDomainObject(restauranteInput);

            return toDto(cadastroRestauranteService.salvar(restaurante));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public RestauranteDto atualizar(@PathVariable Long id,
                                    @RequestBody @Valid RestauranteInputDto restauranteInput) {
        try {
        Restaurante restaurante = toDomainObject(restauranteInput);
        Restaurante restauranteAtual = cadastroRestauranteService.verificarSeExiste(id);

        BeanUtils.copyProperties(restaurante, restauranteAtual,
                "id", "formasPagamento", "endereco", "dataCadastro", "produtos");

            return toDto(cadastroRestauranteService.salvar(restauranteAtual));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public RestauranteDto atualizarParcial(@PathVariable Long id,
                                           @RequestBody Map<String, Object> campos,
                                           HttpServletRequest request) {
        Restaurante restaurante = cadastroRestauranteService.verificarSeExiste(id);

        merge(campos, restaurante, request);

        return atualizar(id, toInputDto(restaurante));
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

    private RestauranteDto toDto(Restaurante restaurante) {
        CozinhaDto cozinhaDto = new CozinhaDto();
        cozinhaDto.setId(restaurante.getCozinha().getId());
        cozinhaDto.setNome(restaurante.getCozinha().getNome());

        RestauranteDto restauranteDto = new RestauranteDto();
        restauranteDto.setId(restaurante.getId());
        restauranteDto.setNome(restaurante.getNome());
        restauranteDto.setTaxaFrete(restaurante.getTaxaFrete());
        restauranteDto.setCozinha(cozinhaDto);

        return restauranteDto;
    }

    private List<RestauranteDto> toDtoList(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(this::toDto)
                .toList();
    }

    private Restaurante toDomainObject(RestauranteInputDto restauranteInput) {
        Restaurante restaurante = new Restaurante();
        restaurante.setNome(restauranteInput.getNome());
        restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());

        Cozinha cozinha = new Cozinha();
        cozinha.setId(restauranteInput.getCozinha().getId());

        restaurante.setCozinha(cozinha);

        return restaurante;
    }

    private RestauranteInputDto toInputDto(Restaurante restaurante) {
        RestauranteInputDto restauranteInputDto = new RestauranteInputDto();
        restauranteInputDto.setNome(restaurante.getNome());
        restauranteInputDto.setTaxaFrete(restaurante.getTaxaFrete());

        CozinhaIdInputDto cozinhaIdInputDto = new CozinhaIdInputDto();
        cozinhaIdInputDto.setId(restaurante.getCozinha().getId());

        restauranteInputDto.setCozinha(cozinhaIdInputDto);

        return restauranteInputDto;
    }

}
