package study.studymatching_backend.account.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import study.studymatching_backend.account.dto.AccountCreateRequest;
import study.studymatching_backend.exception.AlreadyExistsEmailException;
import study.studymatching_backend.exception.AlreadyExistsNicknameException;
import study.studymatching_backend.security.service.RestUserDetailsService;

@Component
@RequiredArgsConstructor
public class SignUpFormValidator implements Validator {

    private final RestUserDetailsService UserDetailsService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(AccountCreateRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        //TODO email, nickname
        AccountCreateRequest accountCreateRequest = (AccountCreateRequest) target;
        if (UserDetailsService.alreadyExistsEmail(accountCreateRequest)) {
            throw new AlreadyExistsEmailException();
        }
        if (UserDetailsService.alreadyExistsNickname(accountCreateRequest)) {
            throw new AlreadyExistsNicknameException();
        }
    }
}
