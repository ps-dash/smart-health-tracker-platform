package in.rw.userService.controller;

import in.rw.userService.LoggingMarkers;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleEntityNotFound(EntityNotFoundException e) {
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
                .addMarker(LoggingMarkers.ERROR)
                .log(errorMessage);
        error.put("Error", errorMessage);

        return error;
    }
}
