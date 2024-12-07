package med.voll.api.infra.errors;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalErrorHandler {

    // =======================================
    // Excepciones relacionadas con HTTP
    // =======================================

    @ExceptionHandler({SQLIntegrityConstraintViolationException.class, IllegalStateException.class})
    public ResponseEntity<Map<String, String>> handleDuplicateRecordException(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("code", "ERR_DUPLICATE_RECORD");
        errorResponse.put("message", "Ya existe un registro con este correo o documento.");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse); // Código 409
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        if (ex.getMessage().contains("Enum")) {
            return buildErrorResponse("ERR_INVALID_SPECIALITY", "La especialidad ingresada no es válida.",
                    HttpStatus.BAD_REQUEST); // Código 400
        }
        return buildErrorResponse("ERR_INVALID_REQUEST", "Solicitud inválida.", HttpStatus.BAD_REQUEST); // Código 400
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        // Crear una lista para almacenar todos los errores
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        });

        // Agregar los errores al cuerpo de la respuesta
        errorResponse.put("code", "ERR_VALIDATION_FAILED");
        errorResponse.put("errors", fieldErrors); // Lista de errores por campo

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse); // Código 400
    }

    // Método común para construir la respuesta de error
    private ResponseEntity<Map<String, String>> buildErrorResponse(String code, String message, HttpStatus status) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("code", code);
        errorResponse.put("message", message);
        return ResponseEntity.status(status).body(errorResponse); // Código 400
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEntityNotFoundException(EntityNotFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("code", "ERR_RECORD_NOT_FOUND");
        errorResponse.put("message", "El recurso solicitado no fue encontrado.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse); // Código 404
    }

    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity<Map<String, String>> handleAuthenticationException(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("code", "ERR_INVALID_CREDENTIALS");
        errorResponse.put("message", "Credenciales inválidas. Verifica tu login y/o clave.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse); // Código 401
    }

    // =======================================
    // Excepciones Genericas
    // =======================================


}