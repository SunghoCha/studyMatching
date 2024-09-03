package study.studymatching_backend.account.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import study.studymatching_backend.domain.Account;
import study.studymatching_backend.dto.RoleResponse;

import java.util.Set;
import java.util.stream.Collectors;

@Getter @Setter
public class AccountCreateResponse {

    private Long id;
    private String nickname;
    private int numberOfUser;
    private Set<RoleResponse> roles;

    @Builder
    public AccountCreateResponse(Long id, String nickname, int numberOfUser, Set<RoleResponse> roles) {
        this.id = id;
        this.nickname = nickname;
        this.numberOfUser = numberOfUser;
        this.roles = roles;
    }

    public static AccountCreateResponse of(Account account, int numberOfUser) {
        return AccountCreateResponse.builder()
                .id(account.getId())
                .nickname(account.getNickname())
                .numberOfUser(numberOfUser)
                .roles(account.getAccountRoles().stream()
                        .map(RoleResponse::of)
                        .collect(Collectors.toSet()))
                .build();
    }
}
