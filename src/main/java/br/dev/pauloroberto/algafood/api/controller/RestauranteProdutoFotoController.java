package br.dev.pauloroberto.algafood.api.controller;

import br.dev.pauloroberto.algafood.api.model.input.FotoProdutoInputDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void atualizarFoto(@PathVariable Long restauranteId,
                              @PathVariable Long produtoId,
                              @Valid FotoProdutoInputDto fotoProdutoInputDto) {
        String nomeArquivo = UUID.randomUUID() + "_" + fotoProdutoInputDto.getArquivo().getOriginalFilename();
        Path arquivoFoto = Path.of("/Users/prvrc/Desktop/upload", nomeArquivo);

        System.out.println(">>> " + arquivoFoto);
        System.out.println(">>> " + fotoProdutoInputDto.getArquivo().getContentType());
        System.out.println(">>> " + fotoProdutoInputDto.getArquivo().getOriginalFilename());
        System.out.println(">>> " + fotoProdutoInputDto.getArquivo().getSize());
        System.out.println(">>> " + fotoProdutoInputDto.getDescricao());

        try {
            fotoProdutoInputDto.getArquivo().transferTo(arquivoFoto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
