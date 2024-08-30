package study.studymatching_backend.exception;

import study.studymatching_backend.exception.dto.ErrorTuple;

public abstract class StudyMatchingException extends RuntimeException {

    public final ErrorTuple errorTuple = new ErrorTuple();

    public StudyMatchingException(String message) {
        super(message);
    }

    public StudyMatchingException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract String getStatusCode();

    public void addValidation(String fieldName, String message) {
        errorTuple.setFieldName(fieldName);
        errorTuple.setMessage(message);
    }
}
