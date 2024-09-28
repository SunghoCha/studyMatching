package study.studymatching_backend.account.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import study.studymatching_backend.domain.Account;
import study.studymatching_backend.dto.ProfileResponse;
import study.studymatching_backend.dto.RoleResponse;

import java.util.Set;
import java.util.stream.Collectors;

@Getter @ToString
public class AccountResponse {

    private Long id;
    private String nickname;
    private String email;
    @JsonIgnore
    private String password;
    private Set<RoleResponse> roles;
    private ProfileResponse profileResponse;
    private NotificationResponse notificationResponse;
    private TagResponse tagResponse;

    @Builder
    private AccountResponse(Long id, String nickname, String email, String password, Set<RoleResponse> roles,
                            ProfileResponse profileResponse, NotificationResponse notificationResponse, TagResponse tagResponse) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.profileResponse = profileResponse;
        this.notificationResponse = notificationResponse;
        this.tagResponse = tagResponse;
    }

    public static AccountResponse of(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .nickname(account.getNickname())
                .email(account.getEmail())
                .password(account.getPassword())
                .roles(account.getAccountRoles().stream()
                        .map(RoleResponse::of)
                        .collect(Collectors.toSet()))
                .profileResponse(ProfileResponse.of(account.getProfile()))
                .notificationResponse(NotificationResponse.of(account.getNotification()))
                .tagResponse(TagResponse.of(account.getAccountTags()))
                .build();
    }
}
