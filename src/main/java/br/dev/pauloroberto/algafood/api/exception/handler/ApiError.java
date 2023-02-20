package br.dev.pauloroberto.algafood.api.exception.handler;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ApiError {

    private LocalDateTime timestamp;
    private String mensagem;
}
