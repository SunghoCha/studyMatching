package study.studymatching_backend.exception.dto;

import study.studymatching_backend.exception.StudyMatchingException;

public class EmailSendFailException extends StudyMatchingException {

    public static final String MESSAGE = "확인 이메일 전송에 실패하였습니다.";

    public EmailSendFailException() {
        super(MESSAGE);
    }

    @Override
    public String getStatusCode() {
        return "400";
    }
}
