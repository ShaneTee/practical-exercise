package com.app.userservice.exceptions;

import com.app.userservice.components.responses.Error;
import javassist.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomRestControllerAdvice{


    /**
     * User not found thrown by User Manager service
     * @param e
     * @return
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Error> handleNotFoundException(UserNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        Error error = new Error(status, e.getMessage());

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
