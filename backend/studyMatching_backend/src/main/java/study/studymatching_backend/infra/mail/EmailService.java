package study.studymatching_backend.infra.mail;

import jakarta.mail.MessagingException;

public interface EmailService {

    void sendEmail(EmailMessage emailMessage) throws MessagingException;
}
