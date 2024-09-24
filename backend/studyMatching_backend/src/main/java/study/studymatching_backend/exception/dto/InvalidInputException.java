package study.studymatching_backend.exception.dto;

import study.studymatching_backend.exception.StudyMatchingException;

public class InvalidInputException extends StudyMatchingException {

    public static String MESSAGE = "유효하지 않은 입력입니다.";

    public InvalidInputException() {
        super(MESSAGE);
    }

    @Override
    public String getStatusCode() {
        return "400";
    }
}
