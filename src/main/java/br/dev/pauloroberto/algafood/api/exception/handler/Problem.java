package br.dev.pauloroberto.algafood.api.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel("Problema")
public class Problem {

    @ApiModelProperty(example = "400", position = 1)
    private Integer status;

    @ApiModelProperty(example = "https://algafood.com.br/dados-invalidos", position = 5)
    private String type;

    @ApiModelProperty(example = "Dados inválidos", position = 10)
    private String title;

    @ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.", position = 15)
    private String detail;

    @ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.", position = 20)
    private String userMessage;

    // No formato ISO-8601 (UTC), a letra 'T' é utilizado como separador entre a data e a hora e o 'Z' indica que o horário é no fuso horário UTC
    @ApiModelProperty(example = "2021-03-01T18:09:02.70844Z", value = "[ AAAA-MM-DDThh:mm:ss.sssZ ] Data e hora do erro no formato ISO-8601 (UTC)", position = 25)
    private OffsetDateTime timestamp;

    @ApiModelProperty(value = "Lista de Objetos ou Campos que geraram o erro (opcional)", position = 30)
    private List<Field> fields;

    @Getter
    @Builder
    @ApiModel("ObjetoProblema")
    public static class Field {
        @ApiModelProperty(example = "Nome do campo")
        private String name;
        @ApiModelProperty(example = "O campo é obrigatório")
        private String userMessage;
    }
}
