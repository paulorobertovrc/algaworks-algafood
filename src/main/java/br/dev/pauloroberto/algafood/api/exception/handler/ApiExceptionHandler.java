package br.dev.pauloroberto.algafood.api.exception.handler;

import br.dev.pauloroberto.algafood.domain.exception.EntidadeEmUsoException;
import br.dev.pauloroberto.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.dev.pauloroberto.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException e,
                                                                  WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        Problem problem = createProblemBuilder(status, e.getMessage(), ProblemType.RECURSO_NAO_ENCONTRADO)
                .build();

        return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException e,
                                                          WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        Problem problem = createProblemBuilder(status, e.getMessage(), ProblemType.ENTIDADE_EM_USO)
                .build();

        return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocio(NegocioException e,
                                                    WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Problem problem = createProblemBuilder(status, e.getMessage(), ProblemType.ERRO_NEGOCIO)
                .build();

        return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUncaught(Exception e, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Problem problem = createProblemBuilder(status,
                "Erro interno inesperado. Tente novamente e, se o problema persistir, contate o administrador do sistema.",
                ProblemType.ERRO_DE_SISTEMA)
                .build();

        e.printStackTrace(); // TODO: remover em produção

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
            return handleInvalidFormat((InvalidFormatException) rootCause,
                    headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBinding((PropertyBindingException) rootCause,
                    headers, status, request);
        }

        Problem problem = createProblemBuilder(status,
                "O corpo da requisição está inválido. Verifique erro de sintaxe.",
                ProblemType.MENSAGEM_INCOMPREENSIVEL)
                .build();

        return handleExceptionInternal(e, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException e,
                                                        HttpHeaders headers,
                                                        HttpStatus status,
                                                        WebRequest request) {
        if (e instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) e,
                    headers, status, request);
        }

        return super.handleTypeMismatch(e, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException e,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
        Problem problem = createProblemBuilder(status,
                String.format("O recurso %s, que você tentou acessar, é inexistente.",
                        e.getRequestURL()),
                ProblemType.RECURSO_NAO_ENCONTRADO)
                .build();

        return handleExceptionInternal(e, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e,
                                                                    HttpHeaders headers,
                                                                    HttpStatus status,
                                                                    WebRequest request) {
        Problem problem = createProblemBuilder(status,
                String.format("O parâmetro de URL '%s' recebeu o valor '%s', que é de um tipo inválido. "
                                + "Corrija e informe um valor compatível com o tipo %s.",
                        e.getName(),
                        e.getValue(),
                        Objects.requireNonNull(e.getRequiredType()).getSimpleName()),
                ProblemType.PARAMETRO_INVALIDO)
                .build();

        return handleExceptionInternal(e, problem, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException e,
                                                         HttpHeaders headers,
                                                         HttpStatus status,
                                                         WebRequest request) {
        String path = joinPath(e.getPath());

        Problem problem = createProblemBuilder(status,
                String.format("A propriedade '%s' não existe. "
                                + "Corrija ou remova essa propriedade e tente novamente.",
                        path),
                ProblemType.PARAMETRO_INVALIDO)
                .build();

        return handleExceptionInternal(e, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException e,
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
