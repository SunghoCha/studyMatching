package study.studymatching_backend.account.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import study.studymatching_backend.domain.Account;

@Getter @Setter
public class AccountCreateResponse {

    private Long id;
    private String nickname;
    private int numberOfUser;

    @Builder
    public AccountCreateResponse(Long id, String nickname, int numberOfUser) {
        this.id = id;
        this.nickname = nickname;
        this.numberOfUser = numberOfUser;
    }

    public static AccountCreateResponse of(Account account, int numberOfUser) {
        return AccountCreateResponse.builder()
                .id(account.getId())
                .nickname(account.getNickname())
                .numberOfUser(numberOfUser)
                .build();
    }
}
