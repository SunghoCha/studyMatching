package study.studymatching_backend.account.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import study.studymatching_backend.domain.Notification;

@Getter @Setter
public class NotificationResponse {

    private boolean studyCreatedByEmail;

    private boolean studyCreatedByWeb;

    private boolean studyEnrollmentResultByEmail;

    private boolean studyEnrollmentResultByWeb;

    private boolean studyUpdatedByEmail;

    private boolean studyUpdatedByWeb;

    @Builder
    private NotificationResponse(boolean studyCreatedByEmail, boolean studyCreatedByWeb, boolean studyEnrollmentResultByEmail,
                                 boolean studyEnrollmentResultByWeb, boolean studyUpdatedByEmail, boolean studyUpdatedByWeb) {
        this.studyCreatedByEmail = studyCreatedByEmail;
        this.studyCreatedByWeb = studyCreatedByWeb;
        this.studyEnrollmentResultByEmail = studyEnrollmentResultByEmail;
        this.studyEnrollmentResultByWeb = studyEnrollmentResultByWeb;
        this.studyUpdatedByEmail = studyUpdatedByEmail;
        this.studyUpdatedByWeb = studyUpdatedByWeb;
    }

    public static NotificationResponse of(Notification notification) {
        return NotificationResponse.builder()
                .studyCreatedByEmail(notification.isStudyCreatedByEmail())
                .studyCreatedByWeb(notification.isStudyCreatedByWeb())
                .studyUpdatedByEmail(notification.isStudyUpdatedByEmail())
                .studyUpdatedByWeb(notification.isStudyUpdatedByWeb())
                .studyEnrollmentResultByEmail(notification.isStudyEnrollmentResultByEmail())
                .studyEnrollmentResultByWeb(notification.isStudyEnrollmentResultByWeb())
                .build();
    }
}
