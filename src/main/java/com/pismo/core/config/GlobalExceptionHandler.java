package com.pismo.core.config;

import com.pismo.core.domain.dto.response.ErrorResponse;
import com.pismo.core.domain.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException exception,
                                                                 final HttpServletRequest request) {
        final ErrorResponse body = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .statusCode(exception.getStatus().value())
                .errorMessage(exception.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
                .status(exception.getStatus())
                .body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(final MethodArgumentNotValidException exception,
                                                                   final HttpServletRequest request) {

        final String errorMessage = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> "%s - %s".formatted(error.getField(), error.getDefaultMessage()))
                .reduce("%s; %s"::formatted).orElse("");

        final ErrorResponse body = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .statusCode(exception.getStatusCode().value())
                .errorMessage(errorMessage)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
                .status(exception.getStatusCode())
                .body(body);
    }

}
