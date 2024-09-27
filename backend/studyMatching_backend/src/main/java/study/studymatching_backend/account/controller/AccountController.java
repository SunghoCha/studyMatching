package study.studymatching_backend.account.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import study.studymatching_backend.account.dto.*;
import study.studymatching_backend.account.util.validator.PasswordFormValidator;
import study.studymatching_backend.account.util.validator.SignUpFormValidator;
import study.studymatching_backend.domain.Account;
import study.studymatching_backend.security.service.RestUserDetailsService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final SignUpFormValidator signUpFormValidator;
    private final PasswordFormValidator passwordFormValidator;
    private final RestUserDetailsService userDetailsService;

    @InitBinder("accountCreateRequest")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpFormValidator);
    }

    @InitBinder("passwordEditRequest")
    public void InitBinder(WebDataBinder webDataBinder) { webDataBinder.addValidators(passwordFormValidator);}

    @PostMapping("/sign-up")
    public ResponseEntity<Long> signUpForm(@Valid @RequestBody AccountCreateRequest accountCreateRequest) {
        Long id = userDetailsService.signup(accountCreateRequest);

        return ResponseEntity.ok().body(id);
    }

    @GetMapping("/check-email-token")
    public ResponseEntity<AccountCreateResponse> checkEmailToken(@RequestParam String token,
                                                                 @RequestParam String email) {
        Account account = userDetailsService.completeRegistration(email, token);
        AccountCreateResponse response = AccountCreateResponse.of(account, userDetailsService.getTotalCount());

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/resend-confirm-email")
    public String resendConfirmEmail(@RequestParam String email) {
        userDetailsService.sendSignUpConfirmEmail(email);
        // TODO 이메일 전송 기능 미완성
        return "수정 중";
    }

    @PatchMapping("/profile/{accountId}")
    public ResponseEntity<AccountResponse> editProfile(@PathVariable(name = "accountId") Long id,
                                                       @Valid @RequestBody ProfileEditRequest profileEditRequest) {
        AccountResponse response = userDetailsService.updateAccountProfile(id, profileEditRequest);
        // TODO AccountResponse 필드값 수정 필요

        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/password/{accountId}")
    public ResponseEntity<AccountResponse> editPassword(@PathVariable(name = "accountId") Long id,
                                                        @Valid @RequestBody PasswordEditRequest passwordEditRequest) {
        AccountResponse response = userDetailsService.updateAccountPassword(id, passwordEditRequest);

        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/notification/{accountId}")
    public ResponseEntity<AccountResponse> editNotification(@PathVariable(name = "accountId") Long id,
                                                            @RequestBody NotificationEditRequest notificationEditRequest) {
        AccountResponse response = userDetailsService.updateAccountNotification(id, notificationEditRequest);

        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/tag/{accountId}")
    public ResponseEntity<AccountResponse> editTag(@PathVariable(name = "accountId") Long id,
                                                   @RequestBody TagEditRequest tagEditRequest) {
        AccountResponse response = userDetailsService.updateAccountTag(id, tagEditRequest);

        return ResponseEntity.ok().body(response);
    }
}

