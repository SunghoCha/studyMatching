package study.studymatching_backend.account.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import study.studymatching_backend.domain.Account;

@Getter @Setter
@NoArgsConstructor
public class AccountRequest {

    private String email;
    private String password;

    @Builder
    public AccountRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static AccountRequest of(Account account) {
        return AccountRequest.builder()
                .email(account.getEmail())
                .password(account.getPassword())
                .build();
    }

    public Account toEntity() {
        return Account.builder()
                .email(email)
                .password(password)
                .build();
    }
}
