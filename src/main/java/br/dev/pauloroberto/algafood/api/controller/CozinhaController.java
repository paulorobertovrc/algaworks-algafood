package br.dev.pauloroberto.algafood.api.controller;

import br.dev.pauloroberto.algafood.api.assembler.CozinhaDomainObjectAssembler;
import br.dev.pauloroberto.algafood.api.assembler.CozinhaDtoAssembler;
import br.dev.pauloroberto.algafood.api.model.CozinhaDto;
import br.dev.pauloroberto.algafood.api.model.input.CozinhaInputDto;
import br.dev.pauloroberto.algafood.api.openapi.controller.CozinhaControllerOpenApi;
import br.dev.pauloroberto.algafood.domain.model.Cozinha;
import br.dev.pauloroberto.algafood.domain.repository.CozinhaRepository;
import br.dev.pauloroberto.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {
    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;
    @Autowired
    private CozinhaDtoAssembler cozinhaDtoAssembler;
    @Autowired
    private CozinhaDomainObjectAssembler cozinhaDomainObjectAssembler;

    @GetMapping
    public Page<CozinhaDto> listar(Pageable pageable) {
        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
        List<CozinhaDto> cozinhasDto = cozinhaDtoAssembler.toDtoList(cozinhasPage.getContent());

        return new PageImpl<>(cozinhasDto, pageable, cozinhasPage.getTotalElements());
    }

    @GetMapping("/{id}")
    public CozinhaDto buscar(@PathVariable Long id) {
        Cozinha cozinha = cadastroCozinhaService.verificarSeExiste(id);

        return cozinhaDtoAssembler.toDto(cozinha);
    }

    @GetMapping("/por-nome")
    public ResponseEntity<List<CozinhaDto>> buscarTodasPorNome(@RequestParam String nome) {
        List<CozinhaDto> cozinhas = cozinhaDtoAssembler.toDtoList(cozinhaRepository.findTodasByNomeContaining(nome));

        return cozinhas.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(cozinhas);
    }

    @GetMapping("/por-nome-exato")
    public ResponseEntity<CozinhaDto> buscarPorNomeExato(@RequestParam String nome) {
        Optional<Cozinha> cozinha = cozinhaRepository.findByNome(nome);

        return cozinha.map(cozinhaEncontrada -> ResponseEntity.ok(cozinhaDtoAssembler.toDto(cozinhaEncontrada)))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/existe-por-nome")
    public ResponseEntity<Boolean> existePorNome(@RequestParam String nome) {
        return ResponseEntity.ok(cozinhaRepository.existsByNomeContaining(nome));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDto adicionar(@RequestBody @Valid CozinhaInputDto cozinhaInput) {
        Cozinha cozinha = cozinhaDomainObjectAssembler.toDomainObject(cozinhaInput);

        return cozinhaDtoAssembler.toDto(cadastroCozinhaService.salvar(cozinha));
    }

    @PutMapping("/{id}")
    public CozinhaDto atualizar(@PathVariable Long id,
                             @RequestBody @Valid CozinhaInputDto cozinhaInput) {
        Cozinha cozinha = cadastroCozinhaService.verificarSeExiste(id);

        cozinhaDomainObjectAssembler.copyToDomainObject(cozinhaInput, cozinha);

        return cozinhaDtoAssembler.toDto(cadastroCozinhaService.salvar(cozinha));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cadastroCozinhaService.excluir(id);
    }

}
