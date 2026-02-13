package com.lamvt.shcedule.exception.handler;
import com.lamvt.shcedule.exception.EntityNotFound;
import com.lamvt.shcedule.exception.InvalidInputException;
import com.lamvt.shcedule.exception.UnauthorizedException;
import com.lamvt.shcedule.payload.response.ApiCode;
import com.lamvt.shcedule.payload.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFound.class)
    public ResponseEntity<ApiResponse<?>> handleEntityNotFound(EntityNotFound e) {
        String message = e.getMessage();
        if (message == null) {
            message = "Entity not found";
        }
        log.error(message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(ApiCode.NOT_FOUND, message));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
                .getFieldErrors()
                .getFirst()
                .getDefaultMessage();
        log.error(message);

        return ResponseEntity.badRequest().body(ApiResponse.error(ApiCode.VALIDATION_ERROR, message));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<?>> handleUnauthorized(UnauthorizedException e) {
        String message = e.getMessage();
        log.error(message);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(ApiCode.UNAUTHORIZED, message));
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidInput(InvalidInputException e) {
        String message = e.getMessage();
        log.error(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(ApiCode.CONFLICT, message));
    }
}
