package study.studymatching_backend.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.studymatching_backend.account.dto.*;
import study.studymatching_backend.account.repository.AccountRepository;
import study.studymatching_backend.account.tag.TagRepository;
import study.studymatching_backend.domain.Account;
import study.studymatching_backend.domain.AccountTag;
import study.studymatching_backend.domain.Role;
import study.studymatching_backend.domain.Tag;
import study.studymatching_backend.exception.*;
import study.studymatching_backend.exception.dto.AccountNotFoundException;
import study.studymatching_backend.infra.mail.EmailService;
import study.studymatching_backend.security.details.RestUserDetails;
import study.studymatching_backend.security.repository.RoleRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RestUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final EmailService emailService;
    private final RoleRepository roleRepository;
    private final TagRepository tagRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Account account = getByEmail(email);
        AccountResponse response = AccountResponse.of(account);
        List<GrantedAuthority> authorities = account.getAuthorities();

        return RestUserDetails.builder()
                .accountResponse(response)
                .authorities(authorities)
                .build();
    }

    private Account getByEmail(String email) {
        return accountRepository.findByEmail(email).orElseThrow(EmailNotFoundException::new);
    }

    public Long signup(AccountCreateRequest accountCreateRequest) {
        Account newAccount = saveAccountWithEncodedPassword(accountCreateRequest);
        setDefaultAccountRole(newAccount);
        newAccount.generateEmailCheckToken();
        sendEmailCheckToken(newAccount);

        return newAccount.getId();
    }

    private void setDefaultAccountRole(Account newAccount) {
        Role role = roleRepository.findByRoleName("ROLE_USER").orElseThrow(RoleNotFoundException::new);
        newAccount.addAccountRole(role);
    }

    private Account saveAccountWithEncodedPassword(AccountCreateRequest accountCreateRequest) {
        String encodedPassword = passwordEncoder.encode(accountCreateRequest.getPassword());
        Account account = accountCreateRequest.toEntity(encodedPassword);

        return accountRepository.save(account);
    }

    private void sendEmailCheckToken(Account newAccount) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(newAccount.getEmail());
        mailMessage.setSubject("스터디매칭, 회원 가입 인증");
        mailMessage.setText("/check-email-token?token=" + newAccount.getEmailCheckToken() + "&email=" + newAccount.getEmail());
        javaMailSender.send(mailMessage);
    }
    public Account completeRegistration(String email, String token) {
        Account account = getByEmail(email);
        if (!account.hasSameToken(token)) {
            throw new InvalidTokenException();
        }
        account.completeRegistration(LocalDateTime.now());
        return account;
    }

    public void sendSignUpConfirmEmail(String email) {
        boolean alreadyExistsEmail = alreadyExistsEmail(AccountCreateRequest.builder().email(email).build());
        if (alreadyExistsEmail) {
            throw new AlreadyExistsEmailException();
        }
        // 이메일 전송 로직
    }

    // 엔티티에 setter 사용 x
    // 엔티티가 dto에 의존? 의존하지 않으면 일일히 필드값 전달? 그렇다고 일일이 필드값 여러 개 전달하면 더 비효율적 -> 범용적인 dto or 클래스에 의존하도록 하자
    // 엔티티의 전체 필드 중 일부 필드만 수정된 결과를 선택적으로 반영하고 싶음
    // 조건문으로 null이나 빈 값이 아닐 때만 반영.
    public AccountResponse updateAccountProfile(Long id, ProfileEditRequest profileEditRequest) {
        Account account = accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
        account.updateProfile(profileEditRequest); // 엔티티가 특정dto에 대해서 알게 되지만 이게 최선인듯. dto를 좀 더 수정에 특화된 형태의 클래스로 변경가능할수도

        return AccountResponse.of(account);
    }

    public AccountResponse updateAccountPassword(Long id, PasswordEditRequest passwordEditRequest) {
        Account account = accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
        String encodedPassword = passwordEncoder.encode(passwordEditRequest.getNewPassword());
        account.updatePassword(encodedPassword);

        return AccountResponse.of(account);
    }

    public AccountResponse updateAccountNotification(Long id, NotificationEditRequest notificationEditRequest) {
        Account account = accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
        account.updateNotification(notificationEditRequest);

        return AccountResponse.of(account);
    }

    public AccountResponse updateAccountTag(Long id, TagEditRequest tagEditRequest) {
        Account account = accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
        Set<Tag> tags = tagRepository.findByTitleIn(tagEditRequest.getTags()); // findAll로 검색해서 containsAll하면 성능 떨어짐.

        if (tags.size() != tagEditRequest.getTags().size()) {
            throw new InvalidTagException();
        }

        Set<AccountTag> accountTags = tags.stream()
                .map(tag -> AccountTag.createAccountTag(account, tag))
                .collect(Collectors.toSet());

        account.updateAccountTag(accountTags);

        return AccountResponse.of(account);
    }

    @Transactional(readOnly = true)
    public int getTotalCount() {
        return (int) accountRepository.count();
    }

    @Transactional(readOnly = true)
    public boolean alreadyExistsEmail(AccountCreateRequest accountCreateRequest) {
        return accountRepository.existsByEmail(accountCreateRequest.getEmail());
    }

    @Transactional(readOnly = true)
    public boolean alreadyExistsNickname(AccountCreateRequest accountCreateRequest) {
        return accountRepository.existsByNickname(accountCreateRequest.getNickname());
    }

}
