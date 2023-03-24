package br.dev.pauloroberto.algafood.api.exception.handler;

import br.dev.pauloroberto.algafood.domain.exception.EntidadeEmUsoException;
import br.dev.pauloroberto.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.dev.pauloroberto.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    public static final String MSG_ERRO_DE_SISTEMA = "Erro interno inesperado. Tente novamente e, se o problema persistir, contate o administrador do sistema.";
    @Autowired
    private MessageSource messageSource;

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
                .userMessage(MSG_ERRO_DE_SISTEMA)
                .build();

        return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUncaught(Exception e, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Problem problem = createProblemBuilder(status,
                MSG_ERRO_DE_SISTEMA,
                ProblemType.ERRO_DE_SISTEMA)
                .build();

        e.printStackTrace(); // TODO: excluir em produção

        return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex,
                                                         HttpHeaders headers,
                                                         HttpStatus status,
                                                         WebRequest request) {


        return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
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
                    .timestamp(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                    .build();
        } else if (body instanceof String) {
            body = Problem.builder()
                    .title((String) body)
                    .status(status.value())
                    .timestamp(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS))
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
                .userMessage(MSG_ERRO_DE_SISTEMA)
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
                .userMessage(MSG_ERRO_DE_SISTEMA)
                .build();

        return handleExceptionInternal(e, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        return handleValidationInternal(e, headers, status, request, e.getBindingResult());
    }

    private ResponseEntity<Object> handleValidationInternal(Exception e,
                                                            HttpHeaders headers,
                                                            HttpStatus status,
                                                            WebRequest request,
                                                            BindingResult bindingResult) {
        List<Problem.Field> problemFields = bindingResult.getFieldErrors().stream()
                .map(fieldError -> {
                    String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());

                    return Problem.Field.builder()
                            .name(fieldError.getField())
                            .userMessage(message)
                            .build();
                })
                .toList();

        Problem problem = createProblemBuilder(status,
                "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.",
                ProblemType.DADOS_INVALIDOS)
                .userMessage(MSG_ERRO_DE_SISTEMA)
                .fields(problemFields)
                .build();

        return handleExceptionInternal(e, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
                                                                      HttpHeaders headers,
                                                                      HttpStatus status,
                                                                      WebRequest request) {
        return ResponseEntity.status(status).headers(headers).build();
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
                .userMessage(MSG_ERRO_DE_SISTEMA)
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
                .userMessage(MSG_ERRO_DE_SISTEMA)
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
                .userMessage(MSG_ERRO_DE_SISTEMA)
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
                .timestamp(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .detail(detail);
    }

}
