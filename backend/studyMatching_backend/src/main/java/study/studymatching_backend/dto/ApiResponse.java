package study.studymatching_backend.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {

    private String code;
    private HttpStatus status;
    private String message;
    private T data;

    private ApiResponse(HttpStatus status, String message, T data) {
        this.code = String.valueOf(status.value());
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> of(HttpStatus status, String message, T data) {
        return new ApiResponse<>(status, message, data);
    }

    public static <T> ApiResponse<T> of(HttpStatus status, T data) {
        return new ApiResponse<>(status, status.name(), data);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return  new ApiResponse<>(HttpStatus.OK, HttpStatus.OK.name(), data);
    }
}
