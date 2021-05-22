package io.github.offjaao.algalog.api.exceptionhandler;

import io.github.offjaao.algalog.domain.exceptions.ClientException;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@AllArgsConstructor
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    private MessageSource messageSource;

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<Object> handleClientException(ClientException ex, WebRequest request) {
        final HttpStatus status = HttpStatus.BAD_REQUEST;

        return handleExceptionInternal(
                ex, Error.of(
                        status.value(),
                        LocalDateTime.now(),
                        ex.getMessage(),
                        null),
                new HttpHeaders(),
                status,
                request
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<SimpleField> fields = new ArrayList<>();

        for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
            fields.add(SimpleField.of(
                    ((FieldError) objectError).getField(),
                    messageSource.getMessage(objectError, LocaleContextHolder.getLocale()))
            );
        }

        final Error error = Error.of(status.value(), LocalDateTime.now(), "Um ou mais campos estão inválidos. " +
                "Faça o preenchimento correto e tente novamente,", fields);

        return handleExceptionInternal(ex, error, headers, status, request);
    }
}
