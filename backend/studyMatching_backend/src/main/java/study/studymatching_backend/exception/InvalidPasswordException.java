package study.studymatching_backend.exception;

public class InvalidPasswordException extends StudyMatchingException {

    public static String MESSAGE = "유효하지 않은 비밀번호입니다.";

    public InvalidPasswordException() {
        super(MESSAGE);
    }

    @Override
    public String getStatusCode() {
        return "400";
    }
}
