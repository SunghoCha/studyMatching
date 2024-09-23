package study.studymatching_backend.account.util.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import study.studymatching_backend.account.dto.PasswordEditRequest;
import study.studymatching_backend.exception.InvalidPasswordException;

@Component
public class PasswordFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return PasswordEditRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PasswordEditRequest passwordForm = (PasswordEditRequest) target;
        if (!passwordForm.getNewPassword().equals(passwordForm.getNewPasswordConfirm())) {
            throw new InvalidPasswordException();
        }
    }
}
