package br.com.software_engineering.infra.web;

import br.com.software_engineering.application.enums.RestResponseStatus;
import br.com.software_engineering.application.exceptions.valueObjectsExceptions.EmailValidationException;
import br.com.software_engineering.application.exceptions.valueObjectsExceptions.PhoneValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid( MethodArgumentNotValidException ex,
            HttpHeaders headers,HttpStatusCode status, WebRequest request) {

        Map<String, String> errorMap = ex.getBindingResult().getAllErrors().stream()
                .collect(Collectors.groupingBy(
                        error -> (error instanceof FieldError fe) ? fe.getField() : "field",
                        Collectors.mapping(
                                error -> error.getDefaultMessage() != null ? error.getDefaultMessage() : "value",
                                Collectors.collectingAndThen(
                                        Collectors.toList(),
                                        list -> list.isEmpty() ? null : list.getFirst()
                                )
                        )
                ));
        RestResponse<Map<String, String>> responseBody = RestResponse.<Map<String, String>>builder()
                .status(RestResponseStatus.ERROR)
                .data(errorMap)
                .message("Validation error")
                .build();

        return ResponseEntity.badRequest().body(responseBody);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        Object requestBody = CaptureRequestBodyAdvice.getRequestBody();

        CaptureRequestBodyAdvice.clear();

        RestResponse<Object> responseBody = RestResponse.<Object>builder()
                .status(RestResponseStatus.ERROR)
                .data(requestBody)
                .message("Non readeable Message")
                .build();

        return ResponseEntity.badRequest().body(responseBody);
    };

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<?> runtimeErrorHandler(RuntimeException ex){
        Object requestBody = CaptureRequestBodyAdvice.getRequestBody();
        CaptureRequestBodyAdvice.clear();

        StackTraceElement[] stack = ex.getStackTrace();
        StackTraceElement origin = Arrays.stream(stack)
                .filter(e -> e.getClassName().contains(".service."))
                .reduce((first, last) -> last)
                .orElse(stack[0]);

        HashMap<String, Object> trace = new HashMap<>();

        trace.put("class", origin.getClassName().substring(origin.getClassName().lastIndexOf('.') + 1));
        trace.put("method", origin.getMethodName());
        trace.put("line", origin.getLineNumber());
        trace.put("message", ex.getMessage());
        trace.put("payload", requestBody);

        log.error("RunTimeException: {}", trace);

        return RestResponse.error(trace, "code error", HttpStatus.INTERNAL_SERVER_ERROR);
    };


    @ExceptionHandler(EmailValidationException.class)
    public ResponseEntity<?> handleEmailError(EmailValidationException ex) {
        return RestResponse.error(ex.getValue(), ex.getMessage());
    }

    @ExceptionHandler(PhoneValidationException.class)
    public ResponseEntity<?> handlePhoneError(PhoneValidationException ex) {
        return RestResponse.error(ex.getValue(), ex.getMessage());
    }
}
