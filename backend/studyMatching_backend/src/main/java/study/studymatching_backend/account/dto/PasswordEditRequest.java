package study.studymatching_backend.account.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter @Setter
public class PasswordEditRequest {

    @NotBlank
    @Length(min = 4, max = 20)
    private String newPassword;

    @NotBlank
    @Length(min = 4, max = 20)
    private String newPasswordConfirm;

    @Builder
    public PasswordEditRequest(String newPassword, String newPasswordConfirm) {
        this.newPassword = newPassword;
        this.newPasswordConfirm = newPasswordConfirm;
    }
}
