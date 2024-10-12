package tmdt.turf.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import tmdt.turf.util.ExceptionMessage;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class GlobalHandleException {
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionMessage> usernameNotFoundExceptionHandler(UsernameNotFoundException exception,
                                                                             WebRequest request) {
        ExceptionMessage errorMessage = new ExceptionMessage(LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionMessage> handleConstraintViolationException(ConstraintViolationException exception,
                                                                               WebRequest request) {
        List<String> errors = new LinkedList<>();
        exception.getConstraintViolations().forEach(violation -> {
            String errorMessage = violation.getMessage();
            errors.add(errorMessage);
        });
        ExceptionMessage errorMessage = new ExceptionMessage(LocalDateTime.now(),
                errors.get(0), request.getDescription(false));
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ExceptionMessage> SQLExceptionHandler(SQLException exception,
                                                                WebRequest request) {
        ExceptionMessage errorMessage = new ExceptionMessage(LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JpaSystemException.class)
    public ResponseEntity<ExceptionMessage> JpaSystemExceptionHandler(JpaSystemException exception,
                                                                      WebRequest request) {
        ExceptionMessage errorMessage = new ExceptionMessage(LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionMessage> authenticationExceptionHandler(AuthenticationException exception,
                                                                           WebRequest request) {
        ExceptionMessage errorMessage = new ExceptionMessage(LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception, WebRequest webRequest) {
        ExceptionMessage errorMessage = new ExceptionMessage(LocalDateTime.now(),
                Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorMessage, exception.getStatusCode());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionMessage> SQLExceptionHandler(NullPointerException exception,
                                                                WebRequest request) {
        ExceptionMessage errorMessage = new ExceptionMessage(LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionMessage> customExceptionHandler(CustomException exception, WebRequest request) {
        ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .description(request.getDescription(true))
                .build();
        return new ResponseEntity<>(exceptionMessage, exception.getHttpStatus());
    }
}
