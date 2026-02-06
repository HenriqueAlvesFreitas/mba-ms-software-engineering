package br.com.software_engineering.infra.web;

import br.com.software_engineering.application.enums.RestResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestResponse<T> {

    private RestResponseStatus status;

    private T data;

    private String message;

    public static <T> ResponseEntity<RestResponse<T>> error(T data, String message) {
        return ResponseEntity.badRequest()
                .body(RestResponse.<T>builder()
                        .status(RestResponseStatus.ERROR)
                        .data(data)
                        .message(message)
                        .build());
    }

    public static <T> ResponseEntity<RestResponse<T>> error(T data, String message, HttpStatus status) {
        return ResponseEntity.status(status)
                .body(RestResponse.<T>builder()
                        .status(RestResponseStatus.ERROR)
                        .data(data)
                        .message(message)
                        .build());
    }

    public static <T> ResponseEntity<RestResponse<T>> success(T data, String message) {
        return ResponseEntity.ok(
                RestResponse.<T>builder()
                        .status(RestResponseStatus.SUCCESS)
                        .data(data)
                        .message(message)
                        .build()
        );
    }
}
