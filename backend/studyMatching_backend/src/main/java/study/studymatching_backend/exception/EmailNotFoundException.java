package study.studymatching_backend.exception;

public class EmailNotFoundException extends StudyMatchingException {

    public static final String MESSAGE = "존재하지 않는 이메일입니다.";

    public EmailNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public String getStatusCode() {
        return "404";
    }
}
