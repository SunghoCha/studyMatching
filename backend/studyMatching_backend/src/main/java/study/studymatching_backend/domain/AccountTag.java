package study.studymatching_backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountTag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Builder
    private AccountTag(Account account, Tag tag) {
        this.account = account;
        this.tag = tag;
    }

    public static AccountTag createAccountTag(Account account, Tag tag) {
        return AccountTag.builder()
                .account(account)
                .tag(tag)
                .build();
    }
}
