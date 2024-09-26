package study.studymatching_backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.studymatching_backend.account.dto.ProfileEditRequest;

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

    public void updateProfile(ProfileEditRequest profileEditRequest) {
        if (profileEditRequest.getBio() != null && !profileEditRequest.getBio().isBlank()) {
            this.bio = profileEditRequest.getBio();
        }
        if (profileEditRequest.getUrl() != null && !profileEditRequest.getUrl().isBlank()) {
            this.url = profileEditRequest.getUrl();
        }
        if (profileEditRequest.getOccupation() != null && !profileEditRequest.getOccupation().isBlank()) {
            this.occupation = profileEditRequest.getOccupation();
        }
        if (profileEditRequest.getLocation() != null && !profileEditRequest.getLocation().isBlank()) {
            this.location = profileEditRequest.getLocation();
        }
    }
}
