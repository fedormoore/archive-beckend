package ru.moore.archive.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException ex) {
        String errors = "";

        for (ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()) {
            if (errors.equals("")) {
                errors = constraintViolation.getMessage();
            } else {
                errors = errors + " " + constraintViolation.getMessage();
            }
        }
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, errors);
        final ResponseEntity responseEntity = new ResponseEntity(error, HttpStatus.BAD_REQUEST);
        return responseEntity;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, errors.toString());
        final ResponseEntity responseEntity = new ResponseEntity(error, HttpStatus.BAD_REQUEST);
        return responseEntity;
    }

    @ExceptionHandler
    public final ResponseEntity<?> handleUserNotFoundException(ErrorTemplate ex) {
        ErrorResponse error = new ErrorResponse(ex.getStatus(), ex.getMessage());
        return new ResponseEntity(error, ex.getStatus());
    }

}
