package study.studymatching_backend.exception.dto;

import study.studymatching_backend.exception.StudyMatchingException;

public class AlreadyExistsNicknameException extends StudyMatchingException {

    public static final String MESSAGE = "이미 존재하는 닉네임입니다.";

    public AlreadyExistsNicknameException() {
        super(MESSAGE);
    }

    public AlreadyExistsNicknameException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getStatusCode() {
        return "400";
    }
}
