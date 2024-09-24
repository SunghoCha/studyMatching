package study.studymatching_backend.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.studymatching_backend.account.dto.NotificationEditRequest;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @OneToOne(mappedBy = "notification")
    private Account account;

    private boolean studyCreatedByEmail = false;

    private boolean studyCreatedByWeb = true;

    private boolean studyEnrollmentResultByEmail = false;

    private boolean studyEnrollmentResultByWeb = true;

    private boolean studyUpdatedByEmail = false;

    private boolean studyUpdatedByWeb = true;

    @Builder
    private Notification(Account account, boolean studyCreatedByEmail, boolean studyCreatedByWeb,
                        boolean studyEnrollmentResultByEmail, boolean studyEnrollmentResultByWeb, boolean studyUpdatedByEmail,
                        boolean studyUpdatedByWeb) {
        this.account = account;
        this.studyCreatedByEmail = studyCreatedByEmail;
        this.studyCreatedByWeb = studyCreatedByWeb;
        this.studyEnrollmentResultByEmail = studyEnrollmentResultByEmail;
        this.studyEnrollmentResultByWeb = studyEnrollmentResultByWeb;
        this.studyUpdatedByEmail = studyUpdatedByEmail;
        this.studyUpdatedByWeb = studyUpdatedByWeb;
    }

    public void updateNotification(NotificationEditRequest notificationEditRequest) {
        this.studyCreatedByEmail = notificationEditRequest.isStudyCreatedByEmail();
        this.studyCreatedByWeb = notificationEditRequest.isStudyCreatedByWeb();
        this.studyUpdatedByEmail = notificationEditRequest.isStudyUpdatedByEmail();
        this.studyUpdatedByWeb = notificationEditRequest.isStudyUpdatedByWeb();
        this.studyEnrollmentResultByEmail = notificationEditRequest.isStudyEnrollmentResultByEmail();
        this.studyEnrollmentResultByWeb = notificationEditRequest.isStudyEnrollmentResultByWeb();
    }
}
