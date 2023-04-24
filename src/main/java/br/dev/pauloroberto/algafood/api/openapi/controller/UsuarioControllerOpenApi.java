package br.dev.pauloroberto.algafood.api.openapi.controller;

import br.dev.pauloroberto.algafood.api.exception.handler.Problem;
import br.dev.pauloroberto.algafood.api.model.UsuarioDto;
import br.dev.pauloroberto.algafood.api.model.input.SenhaInputDto;
import br.dev.pauloroberto.algafood.api.model.input.UsuarioComSenhaInputDto;
import br.dev.pauloroberto.algafood.api.model.input.UsuarioInputDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;

@Api(tags = "Usuários")
public interface UsuarioControllerOpenApi {
    @ApiOperation("Lista os usuários")
    CollectionModel<UsuarioDto> listar();

    @ApiOperation("Busca um usuário por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID do usuário inválido", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)))
    })
    UsuarioDto buscar(@ApiParam(value = "ID do usuário", example = "1", required = true) Long id);

    @ApiOperation("Cadastra um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)))
    })
    UsuarioDto adicionar(@ApiParam(name = "corpo", value = "Representação de um novo usuário", required = true)
                         UsuarioComSenhaInputDto usuarioInput);

    @ApiOperation("Atualiza um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)))
    })
    UsuarioDto atualizar(@ApiParam(value = "ID do usuário", example = "1", required = true) Long id,
                         @ApiParam(name = "corpo", value = "Representação de um usuário com os novos dados",
                                 required = true) UsuarioInputDto usuarioInput);

    @ApiOperation("Altera a senha de um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)))
    })
    void alterarSenha(@ApiParam(value = "ID do usuário", example = "1", required = true) Long id,
                      @ApiParam(name = "corpo", value = "Representação de uma nova senha", required = true)
                      SenhaInputDto senhaInput);

    @ApiOperation("Remove um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário removido"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Problem.class)))
    })
    void remover(@ApiParam(value = "ID do usuário", example = "1", required = true)  Long id);

}
