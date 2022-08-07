package com.letscode.cardgame.controller;

import com.letscode.cardgame.dto.ErrorDTO;
import com.letscode.cardgame.error.ErrorField;
import com.letscode.cardgame.exception.EntityNotFoundException;
import com.letscode.cardgame.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDTO> resourceNotFoundException(final EntityNotFoundException ex) {
        log.error("Erro ao buscar entidade");
        ErrorDTO errorDTO = ErrorDTO.builder()
                .errorMessage(ex.getMessage())
                .statusCode(NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorDTO> authException(final UnauthorizedException ex) {
        log.error("Erro ao autenticar entidade");
        ErrorDTO errorDTO = ErrorDTO.builder()
                .errorMessage(ex.getMessage())
                .statusCode(UNAUTHORIZED.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(UNAUTHORIZED).body(errorDTO);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Void> handleNullPointerException(final NullPointerException ex) {
        log.error("Null pointer exception", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        log.error("ERRO AO VALIDAR CAMPOS");

        List<ErrorField> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            ErrorField errorField = new ErrorField(error.getField(), error.getDefaultMessage());
            errors.add(errorField);
        }

        return ResponseEntity.status(BAD_REQUEST).body(ErrorDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .statusCode(BAD_REQUEST.value())
                        .errorMessage("Erro ao validar campos")
                        .errorFieldList(errors)
                .build());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDTO> handleEmptyPayload(
            HttpMessageNotReadableException ex) {
        log.error("MESSAGE NOT READABLE ERROR");
        return ResponseEntity.status(BAD_REQUEST).body(ErrorDTO.builder()
                        .statusCode(BAD_REQUEST.value())
                        .timestamp(LocalDateTime.now())
                        .errorMessage(ex.getMessage())
                .build());
    }
}
