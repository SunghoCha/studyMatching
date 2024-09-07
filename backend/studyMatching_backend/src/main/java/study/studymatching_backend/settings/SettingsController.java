package study.studymatching_backend.settings;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.studymatching_backend.account.dto.AccountResponse;
import study.studymatching_backend.account.util.annotation.CurrentUser;

@RestController
@RequiredArgsConstructor
public class SettingsController {

    @GetMapping("/settings/profile")
    public String profileUpdateForm(@CurrentUser AccountResponse accountResponse) {
        System.out.println("accountResponse = " + accountResponse);
        return "setting";
    }
}
