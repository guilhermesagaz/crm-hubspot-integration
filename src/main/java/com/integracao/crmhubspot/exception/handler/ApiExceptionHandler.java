package com.integracao.crmhubspot.exception.handler;

import com.integracao.crmhubspot.core.message.MessageHandler;
import com.integracao.crmhubspot.exception.HubspotApiException;
import feign.FeignException;
import feign.RetryableException;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintViolationException;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.integracao.crmhubspot.core.constants.Constants.ChaveMensagem.*;

@RequiredArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler {

    private final MessageHandler messageHandler;

    @ResponseBody
    @ExceptionHandler(value = {IllegalArgumentException.class, ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleIllegalArgument(RuntimeException exception) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(exception.getMessage())
                .message(messageHandler.getMessage(PARAMETRO_INVALIDO.getDescricao()))
                .build();
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorValidationResponse handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        return ErrorValidationResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(exception.toString().split(":")[0])
                .errors(createErrorsValidation(exception.getBindingResult()))
                .build();
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(exception.getMessage())
                .message(messageHandler.getMessage(REQUISICAO_INVALIDA.getDescricao()))
                .build();
    }

    @ResponseBody
    @ExceptionHandler(HubspotApiException.class)
    public ResponseEntity<ErrorResponse> handleHubspotApiException(HubspotApiException exception) {
        return new ResponseEntity<>(
                ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(exception.getHttpStatus().value())
                .detail(Arrays.toString(exception.getStackTrace()))
                .message(messageHandler.getMessage(exception.getMessage()))
                .build()
                , exception.getHttpStatus());
    }

    //Exceções do FeingException
    @ResponseBody
    @ExceptionHandler({FeignException.ServiceUnavailable.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorResponse handleFeignExceptionServiceUnavailable(FeignException exception) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.SERVICE_UNAVAILABLE.value())
                .detail(exception.getMessage())
                .message(messageHandler.getMessage(FEIGN_SERVICE_UNAVAILABLE.getDescricao(), getNomeServico(exception)))
                .build();
    }

    @ResponseBody
    @ExceptionHandler({FeignException.NotFound.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleFeignExceptionNotFound(FeignException exception) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .detail(exception.getMessage())
                .message(messageHandler.getMessage(FEIGN_ENTITY_NOT_FOUND.getDescricao()))
                .build();
    }

    @ResponseBody
    @ExceptionHandler({RetryableException.class})
    @ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
    public ErrorResponse handleFeignExceptionBadRequest(RetryableException exception) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.GATEWAY_TIMEOUT.value())
                .detail(exception.getMessage())
                .message(messageHandler.getMessage(FEIGN_SERVICE_GATEWAY_TIMEOUT.getDescricao(), getNomeServico(exception)))
                .build();
    }

    private String getNomeServico(Exception exception) {
        String nome = null;
        String message = Objects.nonNull(exception.getCause()) ? exception.getCause().getMessage() : exception.getMessage();

        if (StringUtils.isNotBlank(message)) {
            String[] split = message.split("\\.");
            nome = split[0];
        }

        return nome;
    }

    private List<ErrorValidationResponse.Error> createErrorsValidation(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
                .map(objectError -> ErrorValidationResponse.Error.builder()
                        .field(objectError instanceof FieldError ? ((FieldError) objectError).getField() : null)
                        .message(objectError.getDefaultMessage())
                        .build())
                .toList();
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorResponse {
        private LocalDateTime timestamp;
        private Integer status;
        private String detail;
        private String message;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorValidationResponse {
        private LocalDateTime timestamp;
        private Integer status;
        private String error;
        private List<Error> errors = new ArrayList<>();

        @Getter
        @Setter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Error {
            private String field;
            private String message;
        }
    }
}
