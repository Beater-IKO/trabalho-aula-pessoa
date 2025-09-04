package com.aula.pessoa.config.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

// modelo para capturar os erros das validacoes, pode ser usado no projeto mensal
@Getter
public class ErrorMessage {

    private String path;
    private String method;
    private Integer status;
    private String statusMessage;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> errors;

    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusMessage = status.getReasonPhrase();
        this.message = message;
    }

    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message, BindingResult result) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusMessage = status.getReasonPhrase();
        this.message = message;
        addErrors(result);
    }

    public void addErrors(BindingResult result){
        this.errors = new HashMap<>();
        for(FieldError e : result.getFieldErrors()){
            errors.put(e.getField(), e.getDefaultMessage());
        }
    }
}
