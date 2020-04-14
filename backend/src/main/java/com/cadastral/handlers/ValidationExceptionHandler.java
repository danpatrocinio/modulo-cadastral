package com.cadastral.handlers;

import com.cadastral.utils.Utils;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler({ TransactionSystemException.class })
    public ResponseEntity<Object> handleConstraintViolation(Exception ex, WebRequest request) {
        Throwable cause = ((TransactionSystemException) ex).getRootCause();
        if (cause instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) cause).getConstraintViolations();
            String message = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(". ")).concat(".");
            return ResponseEntity.badRequest().body(new HandlerResult(message, "Erro de validação"));
        }
        return ResponseEntity.ok("Ocorreu um erro não tratado.");
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolation(Exception ex, WebRequest request) {
        Throwable cause = ((DataIntegrityViolationException) ex).getRootCause();
        if (cause instanceof JdbcSQLIntegrityConstraintViolationException) {
            String message = cause.getMessage();
            if (Utils.isUniqueViolation(message)) {
                message = message.contains("PESSOA(CPF)") ? "CPF já cadastrado" : message;
                message = message.contains("USUARIO(USERNAME)") ? "Usuário já cadastrado" : message;
            }
            return ResponseEntity.badRequest().body(new HandlerResult(message, "Erro de integridade de dados"));
        }
        return ResponseEntity.ok("Ocorreu um erro não tratado.");
    }

}
