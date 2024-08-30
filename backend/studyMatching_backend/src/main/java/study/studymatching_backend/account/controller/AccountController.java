package study.studymatching_backend.account.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import study.studymatching_backend.account.dto.AccountCreateRequest;
import study.studymatching_backend.account.service.AccountService;
import study.studymatching_backend.account.util.SignUpFormValidator;
import study.studymatching_backend.dto.ApiResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final SignUpFormValidator signUpFormValidator;
    private final AccountService accountService;

    @InitBinder("accountCreateRequest")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpFormValidator);
    }

    @GetMapping("/")
    public String home() {

        return "home";
    }

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<Long>> signUpForm(@Valid @RequestBody AccountCreateRequest accountCreateRequest) {
        Long id = accountService.signup(accountCreateRequest);

        return ResponseEntity.ok().body(ApiResponse.ok(id));
    }

    @GetMapping("/login")
    public String loginForm() {

        return "loginForm";
    }

    @GetMapping("/check-email")
    public String checkEmailForm() {

        return "checkEmailForm";
    }



}
