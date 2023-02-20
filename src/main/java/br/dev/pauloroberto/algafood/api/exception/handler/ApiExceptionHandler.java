package br.dev.pauloroberto.algafood.api.exception.handler;

import br.dev.pauloroberto.algafood.domain.exception.EntidadeEmUsoException;
import br.dev.pauloroberto.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.dev.pauloroberto.algafood.domain.exception.NegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e) {
        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .mensagem(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e) {
        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .mensagem(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(NegocioException e) {
        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .mensagem(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<?> handleHttpMediaTypeNotSupportedException() {
        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .mensagem("O tipo de mídia não é aceito.")
                .build();

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(error);
    }

}
