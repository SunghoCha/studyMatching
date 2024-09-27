package study.studymatching_backend.exception;

public class InvalidTagException extends StudyMatchingException {

    public static String MESSAGE = "유효하지 않은 태그입니다.";

    public InvalidTagException() {
        super(MESSAGE);
    }

    @Override
    public String getStatusCode() {
        return "400";
    }
}
