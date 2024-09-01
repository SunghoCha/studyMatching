package study.studymatching_backend.exception;

public class InvalidTokenException extends StudyMatchingException {

    public static String MESSAGE = "유효하지 않은 토큰입니다.";

    public InvalidTokenException() {
        super(MESSAGE);
    }

    @Override
    public String getStatusCode() {
        return "400";
    }
}
