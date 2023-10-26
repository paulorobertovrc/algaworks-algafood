package br.dev.pauloroberto.algafood.api.v2.controller;

import br.dev.pauloroberto.algafood.api.v2.assembler.CozinhaDomainObjectAssemblerV2;
import br.dev.pauloroberto.algafood.api.v2.assembler.CozinhaDtoAssemblerV2;
import br.dev.pauloroberto.algafood.api.v2.model.CozinhaDtoV2;
import br.dev.pauloroberto.algafood.api.v2.model.input.CozinhaInputDtoV2;
import br.dev.pauloroberto.algafood.api.v2.openapi.controller.CozinhaControllerOpenApiV2;
import br.dev.pauloroberto.algafood.domain.model.Cozinha;
import br.dev.pauloroberto.algafood.domain.repository.CozinhaRepository;
import br.dev.pauloroberto.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v2/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaControllerV2 implements CozinhaControllerOpenApiV2 {

    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;
    @Autowired
    private CozinhaDtoAssemblerV2 cozinhaDtoAssembler;
    @Autowired
    private CozinhaDomainObjectAssemblerV2 cozinhaDomainObjectAssembler;
    @Autowired
    private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @Override
    @GetMapping
    public PagedModel<CozinhaDtoV2> listar(@PageableDefault(size = 5) Pageable pageable) { // default size = 10
        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);

        return pagedResourcesAssembler.toModel(cozinhasPage, cozinhaDtoAssembler);
    }

    @Override
    @GetMapping("/{id}")
    public CozinhaDtoV2 buscar(@PathVariable Long id) {
        Cozinha cozinha = cadastroCozinhaService.verificarSeExiste(id);

        return cozinhaDtoAssembler.toModel(cozinha);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDtoV2 adicionar(@RequestBody @Valid CozinhaInputDtoV2 cozinhaInput) {
        Cozinha cozinha = cozinhaDomainObjectAssembler.toDomainObject(cozinhaInput);

        return cozinhaDtoAssembler.toModel(cadastroCozinhaService.salvar(cozinha));
    }

    @Override
    @PutMapping("/{id}")
    public CozinhaDtoV2 atualizar(@PathVariable Long id,
                             @RequestBody @Valid CozinhaInputDtoV2 cozinhaInput) {
        Cozinha cozinha = cadastroCozinhaService.verificarSeExiste(id);

        cozinhaDomainObjectAssembler.copyToDomainObject(cozinhaInput, cozinha);

        return cozinhaDtoAssembler.toModel(cadastroCozinhaService.salvar(cozinha));
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cadastroCozinhaService.excluir(id);
    }

}
