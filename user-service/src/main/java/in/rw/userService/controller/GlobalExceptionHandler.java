package in.rw.userService.controller;

import in.rw.userService.LoggingMarkers;
import in.rw.userService.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleUserNotFound(UserNotFoundException e) {
        Map<String, String> error = new HashMap<>();
        error.put("Error", e.getMessage());

        return error;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleGenericError(Exception e) {
        Map<String, String> error = new HashMap<>();
        String errorMessage = "An unexpected error occurred!";
        log.atError()
                .setCause(e)
                .addMarker(LoggingMarkers.ERROR)
                .log(errorMessage);
        error.put("Error", errorMessage);
        return error;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handelLoginError(UsernameNotFoundException e) {
        Map<String, String> error = new HashMap<>();
        error.put("Login Error", e.getMessage());

        return error;
    }
}
