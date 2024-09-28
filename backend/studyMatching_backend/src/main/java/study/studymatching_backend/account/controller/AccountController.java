package study.studymatching_backend.account.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import study.studymatching_backend.account.dto.*;
import study.studymatching_backend.account.tag.TagService;
import study.studymatching_backend.account.util.validator.PasswordFormValidator;
import study.studymatching_backend.account.util.validator.SignUpFormValidator;
import study.studymatching_backend.domain.Account;
import study.studymatching_backend.dto.ApiResponse;
import study.studymatching_backend.security.service.RestUserDetailsService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final SignUpFormValidator signUpFormValidator;
    private final PasswordFormValidator passwordFormValidator;
    private final RestUserDetailsService userDetailsService;
    private final TagService tagService;

    @InitBinder("accountCreateRequest")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpFormValidator);
    }

    @InitBinder("passwordEditRequest")
    public void InitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(passwordFormValidator);
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

    //TODO 전체 tag를 요청하는 프론트 로직 미구현상태 (현재는 프론트에서 저장된 특정 태그들 기반으로 임시구현된 상태)
    @GetMapping("/tag")
    public ResponseEntity<TagResponse> getTags() {
        TagResponse response = tagService.getTags();
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/tag/{accountId}") // 엔티티를 새로 추가하거나 데이터를 완전히 새로 추가하는것이 아닌 부분변경이므로 patch가 적절해보임
    public ResponseEntity<AccountResponse> addTag(@PathVariable(name = "accountId") Long id,
                                                  @RequestBody TagEditRequest tagEditRequest) {
        AccountResponse response = userDetailsService.addAccountTag(id, tagEditRequest);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/tag/{accountId}")
    public ResponseEntity<ApiResponse> removeTag(@PathVariable(name = "accountId") Long id,
                                                 @RequestBody TagEditRequest tagEditRequest) {
        boolean isTagsRemoved = userDetailsService.removeAccountTag(id, tagEditRequest);
        if (isTagsRemoved) {
            ApiResponse<Object> response = ApiResponse.builder().description("태그가 성공적으로 삭제되었습니다.").build();
            return ResponseEntity.ok().body(response);
        } else {
            ApiResponse<Object> response = ApiResponse.builder().description("삭제할 태그가 존재하지 않습니다.").build();
            return ResponseEntity.ok().body(response);
        }
    }
}

