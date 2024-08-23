package com.mindhub.todolist.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException rnfe){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rnfe.getMessage());
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<String> handleBadRequestException(BadRequestException bre){
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bre.getMessage());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException cve) {
        String errors = cve.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException dive) {
        // Check for specific constraint violations related to username
        if (dive.getMostSpecificCause().getMessage().contains("USERS(USERNAME")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username is already taken.");
        }

        if (dive.getMostSpecificCause().getMessage().contains("USERS(EMAIL")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Someone else has already registered with that email.");
        }

        // Fallback to a generic conflict message if the specific cause isn't found
        return ResponseEntity.status(HttpStatus.CONFLICT).body("A conflict occurred: " + dive.getMostSpecificCause().getMessage());
    }
}
