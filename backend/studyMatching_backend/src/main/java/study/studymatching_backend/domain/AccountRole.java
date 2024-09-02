package study.studymatching_backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountRole {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_role_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @Builder
    public AccountRole(Account account, Role role) {
        this.account = account;
        this.role = role;
    }

    public static AccountRole createAccountRole(Account account, Role role) {
        return AccountRole.builder()
                .account(account)
                .role(role)
                .build();
    }
}
