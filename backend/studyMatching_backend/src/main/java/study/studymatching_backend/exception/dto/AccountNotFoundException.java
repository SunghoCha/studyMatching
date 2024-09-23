package study.studymatching_backend.exception.dto;

import study.studymatching_backend.exception.StudyMatchingException;

public class AccountNotFoundException extends StudyMatchingException {

    public static final String MESSAGE = "존재하지 않는 회원입니다.";

    public AccountNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public String getStatusCode() {
        return "404";
    }
}
