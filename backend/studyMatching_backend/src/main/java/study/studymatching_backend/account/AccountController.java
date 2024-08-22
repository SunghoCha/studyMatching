package study.studymatching_backend.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountController {

    @GetMapping("/")
    public String home() {

        return "home";
    }

    @GetMapping("/sign-up")
    public String signUpForm() {

        return "sigu-up";
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
