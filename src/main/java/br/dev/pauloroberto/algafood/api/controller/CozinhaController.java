package br.dev.pauloroberto.algafood.api.controller;

import br.dev.pauloroberto.algafood.domain.model.Cozinha;
import br.dev.pauloroberto.algafood.domain.repository.CozinhaRepository;
import br.dev.pauloroberto.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {
    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @GetMapping
    public List<Cozinha> listar() {
        return cozinhaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Cozinha buscar(@PathVariable Long id) {
        return cadastroCozinhaService.verificarSeExiste(id);
    }

    @GetMapping("/por-nome")
    public ResponseEntity<List<Cozinha>> buscarTodasPorNome(@RequestParam String nome) {
        List<Cozinha> cozinhas = cozinhaRepository.findTodasByNomeContaining(nome);

        return cozinhas.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(cozinhas);
    }

    @GetMapping("/por-nome-exato")
    public ResponseEntity<Cozinha> buscarPorNomeExato(@RequestParam String nome) {
        Optional<Cozinha> cozinha = cozinhaRepository.findByNome(nome);

        return cozinha.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/existe-por-nome")
    public ResponseEntity<Boolean> existePorNome(@RequestParam String nome) {
        return ResponseEntity.ok(cozinhaRepository.existsByNomeContaining(nome));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody @Valid Cozinha cozinha) {
        return cadastroCozinhaService.salvar(cozinha);
    }

    @PutMapping("/{id}")
    public Cozinha atualizar(@PathVariable Long id, @RequestBody @Valid Cozinha cozinhaAtualizada) {
        Cozinha cozinha = cadastroCozinhaService.verificarSeExiste(id);

        BeanUtils.copyProperties(cozinhaAtualizada, cozinha, "id");

        return cadastroCozinhaService.salvar(cozinha);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cadastroCozinhaService.excluir(id);
    }
}
