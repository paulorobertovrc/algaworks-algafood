package br.dev.pauloroberto.algafood.api.exception.handler;

import br.dev.pauloroberto.algafood.domain.exception.EntidadeEmUsoException;
import br.dev.pauloroberto.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.dev.pauloroberto.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e,
                                                                  WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        Problem problem = createProblemBuilder(status, e.getMessage(), ProblemType.ENTIDADE_NAO_ENCONTRADA)
                .build();

        return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e,
                                                          WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        Problem problem = createProblemBuilder(status, e.getMessage(), ProblemType.ENTIDADE_EM_USO)
                .build();

        return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(NegocioException e,
                                                    WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Problem problem = createProblemBuilder(status, e.getMessage(), ProblemType.ERRO_NEGOCIO)
                .build();

        return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception e,
                                                             Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {
        if (body == null) {
            body = Problem.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .build();
        } else if (body instanceof String) {
            body = Problem.builder()
                    .title((String) body)
                    .status(status.value())
                    .build();
        }

        return super.handleExceptionInternal(e, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(e);
        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause,
                    headers, status, request);
        }

        Problem problem = createProblemBuilder(status,
                "O corpo da requisição está inválido. Verifique erro de sintaxe.",
                ProblemType.MENSAGEM_INCOMPREENSIVEL)
                .build();

        return handleExceptionInternal(e, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException e,
                                                                HttpHeaders headers,
                                                                HttpStatus status,
                                                                WebRequest request) {
        String path = joinPath(e.getPath());

        Problem problem = createProblemBuilder(status,
                String.format("A propriedade '%s' recebeu o valor '%s', que é de um tipo inválido. "
                                + "O valor informado deve ser do tipo %s.",
                        path,
                        e.getValue(),
                        e.getTargetType().getSimpleName()),
                ProblemType.PARAMETRO_INVALIDO)
                .build();

        return handleExceptionInternal(e, problem, headers, status, request);
    }

    private String joinPath(List<Reference> path) {
        return path.stream()
                .map(Reference::getFieldName)
                .reduce((path1, path2) -> path1 + "." + path2)
                .orElse(null);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status,
                                                        String detail,
                                                        ProblemType problemType) {
        return Problem.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail);
    }

}
