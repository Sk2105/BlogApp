package com.sk.blogapp.handler;

import java.io.FileNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.sk.blogapp.response.BlogErrorResponse;
import com.sk.blogapp.response.ErrorResponse;
import com.sk.blogapp.response.MethodNotAllowedResponse;
import com.sk.blogapp.response.RouteNotFoundResponse;
import com.sk.blogapp.response.UserNotFoundResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        RouteNotFoundResponse routeNotFoundResponse = new RouteNotFoundResponse(
                HttpStatus.NOT_FOUND.value(),
                "Route not found: " + ex.getRequestURL(), ex.getRequestURL());
        return new ResponseEntity<>(routeNotFoundResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        String message = "Method " + ex.getMethod() + " not supported for this route.";
        MethodNotAllowedResponse methodNotAllowedResponse = new MethodNotAllowedResponse(
                HttpStatus.METHOD_NOT_ALLOWED.value(), message, ex.getMethod().toString());
        return new ResponseEntity<>(methodNotAllowedResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        UserNotFoundResponse userNotFoundResponse = new UserNotFoundResponse(HttpStatus.NOT_FOUND.value(),
                ex.getMessage());
        return ResponseEntity.badRequest().body(userNotFoundResponse);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        ErrorResponse userNotFoundResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.badRequest().body(userNotFoundResponse);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleBlogErrorResponse(FileNotFoundException ex) {
        return ResponseEntity.badRequest().body(new BlogErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

}
