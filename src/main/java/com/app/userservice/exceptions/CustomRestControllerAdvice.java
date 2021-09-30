package com.app.userservice.exceptions;

import com.app.userservice.components.responses.Error;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.StringJoiner;

@RestControllerAdvice
public class CustomRestControllerAdvice{


    /**
     * User not found thrown by User Manager service
     * @param e
     * @return
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Error> handleUserNotFoundException(UserNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        Error error = new Error(status, e.getMessage());

        return new ResponseEntity<Error>(error, new HttpHeaders(),status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StringJoiner stringJoiner = new StringJoiner(" ");
        e.getBindingResult().getAllErrors().forEach((error) -> {
            stringJoiner.add(error.getDefaultMessage());
        });

        Error error = new Error(status, stringJoiner.toString());

        return new ResponseEntity<Error>(error, new HttpHeaders(),status);
    }

    /**
     * Validation fail on User object
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Error> handleConstraintViolationExceptions(ConstraintViolationException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Error error = new Error(status, status.getReasonPhrase());

        return new ResponseEntity<Error>(error, new HttpHeaders(),status);
    }


    /**
     * fallback for general exceptions
     * @param e Exception raised during runtime.
     * @return A response entity of internal error during server runtime.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handle(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Error error = new Error(status, status.getReasonPhrase());

        return new ResponseEntity<Error>(error, new HttpHeaders(),status);
    }
}
