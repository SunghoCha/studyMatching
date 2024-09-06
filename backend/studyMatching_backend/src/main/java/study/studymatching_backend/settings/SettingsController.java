package study.studymatching_backend.settings;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class SettingsController {

    @GetMapping("/settings/profile")
    public String profileUpdateForm() {
        return "";
    }
}
