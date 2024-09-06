package study.studymatching_backend.account.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import study.studymatching_backend.account.dto.AccountCreateRequest;
import study.studymatching_backend.account.dto.AccountCreateResponse;
import study.studymatching_backend.account.util.SignUpFormValidator;
import study.studymatching_backend.domain.Account;
import study.studymatching_backend.security.service.RestUserDetailsService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final SignUpFormValidator signUpFormValidator;
    private final RestUserDetailsService userDetailsService;

    @InitBinder("accountCreateRequest")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpFormValidator);
    }

    @GetMapping("/")
    public String home() {

        return "home";
    }

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
}
