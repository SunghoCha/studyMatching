package study.studymatching_backend.main;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.studymatching_backend.account.dto.AccountResponse;
import study.studymatching_backend.account.util.annotation.CurrentUser;
import study.studymatching_backend.domain.Account;
import study.studymatching_backend.dto.ApiResponse;
import study.studymatching_backend.security.details.RestUserDetails;

@RestController
public class MainController {

    @GetMapping("/")
    public ResponseEntity<AccountResponse> home(@CurrentUser AccountResponse accountResponse) {

        return ResponseEntity.ok().body(accountResponse);
    }
}
