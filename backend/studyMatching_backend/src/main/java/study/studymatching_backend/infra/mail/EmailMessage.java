package study.studymatching_backend.infra.mail;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
public class EmailMessage {

    private final String to;
    private final String subject;
    private final String message;

    @Builder
    public EmailMessage(String to, String subject, String message) {
        this.to = to;
        this.subject = subject;
        this.message = message;
    }
}
