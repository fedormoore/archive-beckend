package ru.moore.archive.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class ErrorTemplate extends RuntimeException{

    private HttpStatus status;
    private String message;

    public ErrorTemplate(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
