package smit.homework.bookloan.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import smit.homework.bookloan.exception.BookNotFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler to capture and handle custom {@link BookNotFoundException} exception.
 *
 * @author Mari-Liis Pihlapuu
 * Date: 17.09.2024
 */
@ControllerAdvice
public class BookLoanExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCustomException(BookNotFoundException ex) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", ex.getMessage());

        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }
}
