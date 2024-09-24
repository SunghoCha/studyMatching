package study.studymatching_backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import study.studymatching_backend.domain.Profile;

@Getter @Setter
public class ProfileResponse {

    private String bio;
    private String url;
    private String occupation;
    private String location;

    @Builder
    private ProfileResponse(String bio, String url, String occupation, String location) {
        this.bio = bio;
        this.url = url;
        this.occupation = occupation;
        this.location = location;
    }

    public static ProfileResponse of(Profile profile) {
        return ProfileResponse.builder()
                .bio(profile.getBio())
                .url(profile.getUrl())
                .occupation(profile.getOccupation())
                .location(profile.getLocation())
                .build();
    }
}
