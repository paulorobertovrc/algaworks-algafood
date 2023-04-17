package br.dev.pauloroberto.algafood.api.controller;

import br.dev.pauloroberto.algafood.api.assembler.CozinhaDomainObjectAssembler;
import br.dev.pauloroberto.algafood.api.assembler.CozinhaDtoAssembler;
import br.dev.pauloroberto.algafood.api.model.CozinhaDto;
import br.dev.pauloroberto.algafood.api.model.input.CozinhaInputDto;
import br.dev.pauloroberto.algafood.domain.model.Cozinha;
import br.dev.pauloroberto.algafood.domain.repository.CozinhaRepository;
import br.dev.pauloroberto.algafood.domain.service.CadastroCozinhaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cozinhas")
@Api(tags = "Cozinhas")
public class CozinhaController {
    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;
    @Autowired
    private CozinhaDtoAssembler cozinhaDtoAssembler;
    @Autowired
    private CozinhaDomainObjectAssembler cozinhaDomainObjectAssembler;

    @GetMapping
    @ApiOperation("Lista as cozinhas")
    public Page<CozinhaDto> listar(Pageable pageable) {
        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
        List<CozinhaDto> cozinhasDto = cozinhaDtoAssembler.toDtoList(cozinhasPage.getContent());

        return new PageImpl<>(cozinhasDto, pageable, cozinhasPage.getTotalElements());
    }

    @GetMapping("/{id}")
    @ApiOperation("Busca uma cozinha por ID")
    public CozinhaDto buscar(@PathVariable Long id) {
        Cozinha cozinha = cadastroCozinhaService.verificarSeExiste(id);

        return cozinhaDtoAssembler.toDto(cozinha);
    }

    @GetMapping("/por-nome")
    @ApiOperation("Busca uma cozinha por nome")
    public ResponseEntity<List<CozinhaDto>> buscarTodasPorNome(@RequestParam String nome) {
        List<CozinhaDto> cozinhas = cozinhaDtoAssembler.toDtoList(cozinhaRepository.findTodasByNomeContaining(nome));

        return cozinhas.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(cozinhas);
    }

    @GetMapping("/por-nome-exato")
    @ApiOperation("Busca uma cozinha por nome exato")
    public ResponseEntity<CozinhaDto> buscarPorNomeExato(@RequestParam String nome) {
        Optional<Cozinha> cozinha = cozinhaRepository.findByNome(nome);

        return cozinha.map(cozinhaEncontrada -> ResponseEntity.ok(cozinhaDtoAssembler.toDto(cozinhaEncontrada)))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/existe-por-nome")
    @ApiOperation("Verifica se existe uma cozinha com o nome informado")
    public ResponseEntity<Boolean> existePorNome(@RequestParam String nome) {
        return ResponseEntity.ok(cozinhaRepository.existsByNomeContaining(nome));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Cadastra uma cozinha")
    public CozinhaDto adicionar(@RequestBody @Valid CozinhaInputDto cozinhaInput) {
        Cozinha cozinha = cozinhaDomainObjectAssembler.toDomainObject(cozinhaInput);

        return cozinhaDtoAssembler.toDto(cadastroCozinhaService.salvar(cozinha));
    }

    @PutMapping("/{id}")
    @ApiOperation("Atualiza uma cozinha")
    public CozinhaDto atualizar(@PathVariable Long id,
                             @RequestBody @Valid CozinhaInputDto cozinhaInput) {
        Cozinha cozinha = cadastroCozinhaService.verificarSeExiste(id);

        cozinhaDomainObjectAssembler.copyToDomainObject(cozinhaInput, cozinha);

        return cozinhaDtoAssembler.toDto(cadastroCozinhaService.salvar(cozinha));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Remove uma cozinha")
    public void remover(@PathVariable Long id) {
        cadastroCozinhaService.excluir(id);
    }

}
