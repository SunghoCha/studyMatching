package study.studymatching_backend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@EqualsAndHashCode(of = {"account", "tag"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"account_id", "tag_id"}) // Service 단에서도 중복방지 로직 설정해야함
})
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
