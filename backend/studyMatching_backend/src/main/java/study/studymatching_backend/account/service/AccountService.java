package study.studymatching_backend.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.studymatching_backend.account.dto.AccountCreateRequest;
import study.studymatching_backend.account.repository.AccountRepository;
import study.studymatching_backend.domain.Account;
import study.studymatching_backend.exception.EmailNotFoundException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    public boolean alreadyExistsEmail(AccountCreateRequest accountCreateRequest) {
        return accountRepository.existsByEmail(accountCreateRequest.getEmail());
    }

    public boolean alreadyExistsNickname(AccountCreateRequest accountCreateRequest) {
        return accountRepository.existsByNickname(accountCreateRequest.getNickname());
    }

    @Transactional
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

    public Account getByEmail(String email) {
        return accountRepository.findByEmail(email).orElseThrow(EmailNotFoundException::new);
    }

    public int getTotalCount() {
        return (int) accountRepository.count();
    }
}
