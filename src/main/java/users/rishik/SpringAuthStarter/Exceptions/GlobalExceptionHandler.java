package users.rishik.SpringAuthStarter.Exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.security.SignatureException;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 400: Validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return buildResponse(HttpStatus.BAD_REQUEST, "Validation Error", errorMessage, request, ex);
    }

    // 400: Invalid enum or malformed JSON
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidEnum(HttpMessageNotReadableException ex, HttpServletRequest request) {
        String message = "Invalid input (possibly wrong enum value or bad JSON): " + ex.getMostSpecificCause().getMessage();
        return buildResponse(HttpStatus.BAD_REQUEST, "Bad Request", message, request, ex);
    }

    // 400: Query/path constraint violations
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        String message = ex.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining("; "));
        return buildResponse(HttpStatus.BAD_REQUEST, "Constraint Violation", message, request, ex);
    }

    // 400: Missing query parameters
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingParam(MissingServletRequestParameterException ex, HttpServletRequest request) {
        String message = "Missing parameter: " + ex.getParameterName();
        return buildResponse(HttpStatus.BAD_REQUEST, "Bad Request", message, request, ex);
    }

    // 400: Missing path variables
    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingPathVar(MissingPathVariableException ex, HttpServletRequest request) {
        String message = "Missing path variable: " + ex.getVariableName();
        return buildResponse(HttpStatus.BAD_REQUEST, "Bad Request", message, request, ex);
    }

    // 400: Query/path parameter type mismatch
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        Class<?> requiredType = ex.getRequiredType();
        String expectedType = (requiredType != null)? requiredType.getName(): "unknown";
        String message = "Invalid value for '" + ex.getName() + "': expected " + expectedType;
        return buildResponse(HttpStatus.BAD_REQUEST, "Type Mismatch", message, request, ex);
    }

    // 400: Message conversion failure (e.g., JSON to DTO)
    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<ApiErrorResponse> handleConversionError(HttpMessageConversionException ex, HttpServletRequest request) {
        String message = "Malformed request body: " + ex.getMostSpecificCause().getMessage();
        return buildResponse(HttpStatus.BAD_REQUEST, "Malformed Request", message, request, ex);
    }

    // 404: No matching endpoint
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFoundPath(NoHandlerFoundException ex, HttpServletRequest request) {
        String message = "No endpoint found for path: " + request.getRequestURI();
        return buildResponse(HttpStatus.NOT_FOUND, "Not Found", message, request, ex);
    }

    // 404: No resource found
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiErrorResponse> notFoundResource(NoResourceFoundException ex, HttpServletRequest request) {
        String message = "No resource found for path: " + request.getRequestURI();
        return buildResponse(HttpStatus.NOT_FOUND, "Not Found", message, request, ex);
    }

    // 405: Wrong HTTP method
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.METHOD_NOT_ALLOWED, "Method Not Allowed", ex.getMessage(), request, ex);
    }

    // 415: Unsupported media type
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse> handleUnsupportedMediaType(HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Unsupported Media Type", ex.getMessage(), request, ex);
    }

    // 403: User is authenticated but not authorized
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.FORBIDDEN, "Forbidden", "You do not have permission to access this resource.", request, ex);
    }

    // 401: Auth failures
    @ExceptionHandler({
            BadCredentialsException.class,
            UsernameNotFoundException.class,
            AccountExpiredException.class,
            LockedException.class,
            DisabledException.class,
            CredentialsExpiredException.class,
            AuthenticationCredentialsNotFoundException.class
    })
    public ResponseEntity<ApiErrorResponse> handleAuthentication(AuthenticationException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Unauthorized", ex.getMessage(), request, ex);
    }

    @ExceptionHandler({ ExpiredJwtException.class, MalformedJwtException.class, SignatureException.class })
    public ResponseEntity<ApiErrorResponse> handleJwtErrors(Exception ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "JWT Error", ex.getMessage(), request, ex);
    }


    // 409: DB conflicts (e.g., duplicate email)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT, "Data Conflict", "Database constraint violation.", request, ex);
    }

    // 400: Illegal arguments or state
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<ApiErrorResponse> handleIllegalArgs(RuntimeException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Invalid Input", ex.getMessage(), request, ex);
    }

    // 404: Custom Not Found
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(NotFoundException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request, ex);
    }

    // 500: All other unhandled exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnhandled(Exception ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "An unexpected error occurred.", request, ex);
    }

    // 🔄 Centralized logging and response building
    private ResponseEntity<ApiErrorResponse> buildResponse(HttpStatus status, String error, String message, HttpServletRequest request, Exception ex) {
        ApiErrorResponse response = new ApiErrorResponse(
                status.value(),
                error,
                message,
                request.getRequestURI()
        );

        // Logging based on status
        if (status.is5xxServerError()) {
            log.error("500 Error at [{}]: {} | Exception: {}", request.getRequestURI(), message, ex.getClass().getSimpleName());
        } else if (status.is4xxClientError()) {
            log.warn("{} {} at [{}]: {} | Exception: {}", status.value(), error, request.getRequestURI(), message, ex.getClass().getSimpleName());
        }

        return new ResponseEntity<>(response, status);
    }
}
