package study.studymatching_backend.exception.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import study.studymatching_backend.exception.StudyMatchingException;
import study.studymatching_backend.exception.dto.ErrorResponse;
import study.studymatching_backend.exception.dto.ErrorTuple;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.debug("methodArgumentNotValidException handle : {}", e.getClass());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .build();

        e.getFieldErrors().forEach(fieldError -> {
            errorResponse.addValidation(ErrorTuple.builder()
                            .fieldName(fieldError.getField())
                            .message(fieldError.getDefaultMessage())
                    .build());
                }
        );
        return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(StudyMatchingException.class)
    public ResponseEntity<ErrorResponse> studyMatchingException(StudyMatchingException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(e.getStatusCode())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(Integer.parseInt(e.getStatusCode())).body(errorResponse);
    }
}
