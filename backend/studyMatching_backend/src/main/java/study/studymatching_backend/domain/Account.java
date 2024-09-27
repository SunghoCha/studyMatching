package study.studymatching_backend.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import study.studymatching_backend.account.dto.ProfileEditRequest;
import study.studymatching_backend.account.dto.NotificationEditRequest;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String nickname;

    private String password;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<AccountRole> accountRoles = new LinkedHashSet<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id")
    private Notification notification;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Set<AccountTag> accountTags = new HashSet<>();

    private boolean emailVerified;

    private String emailCheckToken;

    private LocalDateTime joinedAt;

    @Builder
    public Account(String email, String nickname, String password, Notification notification,
                   Profile profile, boolean emailVerified, String emailCheckToken, LocalDateTime joinedAt) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.notification = notification != null ? notification : new Notification();
        this.profile = profile != null ? profile : new Profile();
        this.emailVerified = emailVerified;
        this.emailCheckToken = emailCheckToken;
        this.joinedAt = joinedAt;
    }

    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
    }

    public boolean hasSameToken(String token) {
        return this.emailCheckToken.equals(token);
    }

    public void completeRegistration(LocalDateTime now) {
        this.emailVerified = true;
        this.joinedAt = now;
    }

    public List<GrantedAuthority> getAuthorities() {
        return accountRoles.stream()
                .map(accountRole -> new SimpleGrantedAuthority(accountRole.getRole().getRoleName()))
                .collect(Collectors.toList());
    }

    public void updateProfile(ProfileEditRequest profileEditRequest) {
        if (this.profile == null) {
            this.profile = new Profile();
        }
        this.profile.updateProfile(profileEditRequest);
    }

    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void addAccountRole(Role role) {
        this.accountRoles.add(AccountRole.createAccountRole(this, role));
    }

    public void updateNotification(NotificationEditRequest notificationEditRequest) {
        if (this.notification == null) {
            this.notification = new Notification();
        }
        this.notification.updateNotification(notificationEditRequest);
    }

    public void updateAccountTag(Set<AccountTag> accountTags) {
        this.accountTags.addAll(accountTags);
    }
}
