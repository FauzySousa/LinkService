package com.fauzydev.linkservice.exception;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fauzydev.linkservice.dto.response.ErrorResponse;
import com.fauzydev.linkservice.dto.response.ValidationErrorDetais;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GobalExceptionHandler {

    @ExceptionHandler(UrlNotfoundException.class)
    public ResponseEntity<ErrorResponse> handlerUrlNotFound(UrlNotfoundException e, HttpServletRequest request){

        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponse response = new ErrorResponse(Instant.now(),
            status.value(),
            "Recurso não encontrado",
            e.getMessage(),
            request.getRequestURI()
        );

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handlerValidationError(MethodArgumentNotValidException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<ValidationErrorDetais> validationErrors = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(fieldError -> new ValidationErrorDetais(fieldError.getField(),fieldError.getDefaultMessage()))
            .toList();

        ErrorResponse response = new ErrorResponse(
            Instant.now(),
            status.value(), 
            "Erro de validação nos campos informados",
            "Um ou mais campos estão inválidos.",
            request.getRequestURI(),
            validationErrors);

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handlerGenericError(Exception e, HttpServletRequest request){

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse response = new ErrorResponse(
            Instant.now(), 
            status.value(), 
            "Erro interno no servidor",
            "Ocorreu um erro inesperado em nosso sistema. Tente novamente mais tarde.",
            request.getRequestURI()
        );
        return ResponseEntity.status(status).body(response);
    }
}
