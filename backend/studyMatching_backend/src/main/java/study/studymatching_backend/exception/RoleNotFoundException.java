package study.studymatching_backend.exception;

public class RoleNotFoundException extends StudyMatchingException {

    public static final String MESSAGE = "존재하지 않는 권한입니다.";

    public RoleNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public String getStatusCode() {
        return "404";
    }
}
