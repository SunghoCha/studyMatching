package study.studymatching_backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.studymatching_backend.account.dto.AccountEditRequest;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;

    @OneToOne(mappedBy = "profile")
    private Account account;

    private String bio;

    private String url;

    private String occupation;

    private String location;

    private String profileImage;

    public void updateProfile(AccountEditRequest accountEditRequest) {
        if (accountEditRequest.getBio() != null && !accountEditRequest.getBio().isBlank()) {
            this.bio = accountEditRequest.getBio();
        }
        if (accountEditRequest.getUrl() != null && !accountEditRequest.getUrl().isBlank()) {
            this.url = accountEditRequest.getUrl();
        }
        if (accountEditRequest.getOccupation() != null && !accountEditRequest.getOccupation().isBlank()) {
            this.occupation = accountEditRequest.getOccupation();
        }
        if (accountEditRequest.getLocation() != null && !accountEditRequest.getLocation().isBlank()) {
            this.location = accountEditRequest.getLocation();
        }
    }
}
