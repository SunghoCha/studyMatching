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
import study.studymatching_backend.account.dto.AccountCreateRequest;
import study.studymatching_backend.account.dto.AccountResponse;
import study.studymatching_backend.account.repository.AccountRepository;
import study.studymatching_backend.domain.Account;
import study.studymatching_backend.exception.AlreadyExistsEmailException;
import study.studymatching_backend.exception.EmailNotFoundException;
import study.studymatching_backend.exception.InvalidTokenException;
import study.studymatching_backend.infra.mail.EmailService;
import study.studymatching_backend.security.details.RestUserDetails;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RestUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final EmailService emailService;

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

    @Transactional(readOnly = true)
    public boolean alreadyExistsEmail(AccountCreateRequest accountCreateRequest) {
        return accountRepository.existsByEmail(accountCreateRequest.getEmail());
    }

    @Transactional(readOnly = true)
    public boolean alreadyExistsNickname(AccountCreateRequest accountCreateRequest) {
        return accountRepository.existsByNickname(accountCreateRequest.getNickname());
    }

    public Long signup(AccountCreateRequest accountCreateRequest) {
        Account newAccount = saveAccountWithEncodedPassword(accountCreateRequest);
        newAccount.generateEmailCheckToken();
        sendEmailCheckToken(newAccount);

        return newAccount.getId();
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

    @Transactional(readOnly = true)
    public int getTotalCount() {
        return (int) accountRepository.count();
    }

    public Account completeRegistration(String email, String token) {
        Account account = getByEmail(email);
        if (!account.hasSameToken(token)) {
            throw new InvalidTokenException();
        }
        account.completeRegistration(LocalDateTime.now());
        return account;
    }

    private Account getByEmail(String email) {
        return accountRepository.findByEmail(email).orElseThrow(EmailNotFoundException::new);
    }

    public void sendSignUpConfirmEmail(String email) {
        boolean alreadyExistsEmail = alreadyExistsEmail(AccountCreateRequest.builder().email(email).build());
        if (alreadyExistsEmail) {
            throw new AlreadyExistsEmailException(); 
        }
        // 이메일 전송 로직
    }
}
