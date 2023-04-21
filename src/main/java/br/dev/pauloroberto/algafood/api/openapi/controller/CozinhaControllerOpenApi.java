package br.dev.pauloroberto.algafood.api.openapi.controller;

import br.dev.pauloroberto.algafood.api.exception.handler.Problem;
import br.dev.pauloroberto.algafood.api.model.CozinhaDto;
import br.dev.pauloroberto.algafood.api.model.input.CozinhaInputDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {
    @ApiOperation("Lista as cozinhas")
    Page<CozinhaDto> listar(Pageable pageable);

    @ApiOperation("Busca uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID da cozinha inválido", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            )),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            ))
    })
    CozinhaDto buscar(@ApiParam(value = "ID de uma cozinha", example = "1", required = true) Long id);

    @ApiOperation("Busca uma cozinha por nome")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Nome da cozinha inválido", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            )),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            ))
    })
    ResponseEntity<List<CozinhaDto>> buscarTodasPorNome(@ApiParam(value = "Nome de uma cozinha", example = "Brasileira",
            required = true) String nome);

    @ApiOperation("Busca uma cozinha por nome exato")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Nome da cozinha inválido", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            )),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            ))
    })
    ResponseEntity<CozinhaDto> buscarPorNomeExato(@ApiParam(value = "Nome de uma cozinha", example = "Brasileira",
            required = true) String nome);

    @ApiOperation("Verifica se existe uma cozinha com o nome informado")
    @ApiResponse(responseCode = "400", description = "Nome da cozinha inválido", content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = Problem.class)))
    ResponseEntity<Boolean> existePorNome(@ApiParam(value = "Nome de uma cozinha", example = "Brasileira",
            required = true) String nome);

    @ApiOperation("Cadastra uma cozinha")
    @ApiResponse(responseCode = "201", description = "Cozinha cadastrada", content = @Content(
            schema = @Schema(implementation = Problem.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    CozinhaDto adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true)
                         CozinhaInputDto cozinhaInput);

    @ApiOperation("Atualiza uma cozinha")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cozinha atualizada", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            )),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            ))
    })
    CozinhaDto atualizar(@ApiParam(value = "ID de uma cidade", required = true) Long id,
                         @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados",
                                 required = true) CozinhaInputDto cozinhaInput);

    @ApiOperation("Remove uma cozinha")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cozinha removida", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            )),
            @ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)
            ))
    })
    void remover(@ApiParam(value = "ID de uma cidade", required = true) Long id);

}
