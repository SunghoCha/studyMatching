package study.studymatching_backend.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import study.studymatching_backend.domain.Account;
import study.studymatching_backend.dto.RoleResponse;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class AccountResponse {

    private Long id;
    private String nickname;
    private String email;
    private String password;
    private Set<RoleResponse> roles;

    @Builder
    private AccountResponse(Long id, String nickname, String email, String password, Set<RoleResponse> roles) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
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
                .build();
    }
}
