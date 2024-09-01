package study.studymatching_backend.exception;

import lombok.Getter;
import study.studymatching_backend.exception.dto.ErrorTuple;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class StudyMatchingException extends RuntimeException {

    public StudyMatchingException(String message) {
        super(message);
    }

    public StudyMatchingException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract String getStatusCode();

}
