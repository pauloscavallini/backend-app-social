package dev.cavallini.social.infra;

import dev.cavallini.social.infra.dto.ApiErrorDTO;
import dev.cavallini.social.infra.exceptions.InvalidPostException;
import dev.cavallini.social.infra.exceptions.OperationNotAllowedException;
import dev.cavallini.social.infra.exceptions.PostNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> userNotExceptionFoundHandler(UsernameNotFoundException e) {
        return ResponseEntity.status(404).body(new ApiErrorDTO("User not found"));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorDTO> badCredentialsExceptionHandler(BadCredentialsException e) {
        return ResponseEntity.status(401).body(new ApiErrorDTO("Incorrect username or password"));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorDTO> authenticationExceptionHandler(AuthenticationException e) {
        return ResponseEntity.status(404).body(new ApiErrorDTO("Authentication error: " + e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDTO> exceptionHandler(Exception e) {
        return ResponseEntity.internalServerError().body(new ApiErrorDTO(e.getMessage()));
    }

    @ExceptionHandler(InvalidPostException.class)
    public ResponseEntity<ApiErrorDTO> invalidPostHandler(InvalidPostException e) {
        return ResponseEntity.badRequest().body(new ApiErrorDTO("Post deve possuir entre 1 e 100 caracteres"));
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> postNotFoundHandler(PostNotFoundException e) {
        return ResponseEntity.status(404).body(new ApiErrorDTO("Nao encontrado"));
    }

    @ExceptionHandler(OperationNotAllowedException.class)
    public ResponseEntity<ApiErrorDTO> operationNotAllowedHandler(OperationNotAllowedException e) {
        return ResponseEntity.status(403).body(new ApiErrorDTO("Sem permissoes necessarias"));
    }
}
