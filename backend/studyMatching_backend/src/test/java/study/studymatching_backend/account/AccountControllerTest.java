package study.studymatching_backend.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.With;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import study.studymatching_backend.WithAccount;
import study.studymatching_backend.account.dto.*;
import study.studymatching_backend.account.repository.AccountRepository;
import study.studymatching_backend.domain.Account;
import study.studymatching_backend.exception.EmailNotFoundException;
import study.studymatching_backend.security.details.RestUserDetails;
import study.studymatching_backend.security.service.RestUserDetailsService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JavaMailSender javaMailSender;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RestUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterEach
    void clean() {
        accountRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입시 닉네임 값은 필수")
    void signUpSubmit_with_wrong_input() throws Exception {
        // given
        AccountCreateRequest request = AccountCreateRequest.builder()
                .nickname("")
                .email("example1@gmail.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(csrf())
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("회원가입 성공 테스트")
    void signUpSubmit_with_correct_input() throws Exception {
        // given
        AccountCreateRequest request = AccountCreateRequest.builder()
                .nickname("nickname1")
                .email("example1@gmail.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(csrf())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        // then
        Account findAccount = accountRepository.findByEmail(request.getEmail()).orElseThrow(EmailNotFoundException::new);
        assertThat(findAccount.getEmail()).isEqualTo(request.getEmail());
        assertThat(findAccount.getNickname()).isEqualTo(request.getNickname());

        then(javaMailSender).should().send(ArgumentMatchers.any(SimpleMailMessage.class));
    }

    @Test
    @DisplayName("인증 메일 확인 - 존재하지않는 이메일")
    void checkEmailToken_with_wrong_email() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/check-email-token")
                        .param("token", "wrongToken")
                        .param("email", "wrongEmail"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("404"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("존재하지 않는 이메일입니다."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("인증 메일 확인 - 유효하지 않은 토큰")
    void checkEmailToken_with_wrong_token() throws Exception {
        Account account = Account.builder()
                .nickname("nickname1")
                .email("example1@gmail.com")
                .password("1234")
                .build();

        Account savedAccount = accountRepository.save(account);
        account.generateEmailCheckToken();

        mockMvc.perform(MockMvcRequestBuilders.get("/check-email-token")
                        .param("token", "wrongToken")
                        .param("email", account.getEmail()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("유효하지 않은 토큰입니다."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("인증 메일 확인 - 입력값 정상")
    void checkEmailToken_with_correct_input() throws Exception {
        Account account = Account.builder()
                .nickname("nickname1")
                .email("example1@gmail.com")
                .password("1234")
                .build();
        account.generateEmailCheckToken();
        Account savedAccount = accountRepository.save(account);

        mockMvc.perform(MockMvcRequestBuilders.get("/check-email-token")
                        .param("token", account.getEmailCheckToken())
                        .param("email", "example1@gmail.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void login_with_correct_input() throws Exception {
        // given
        AccountCreateRequest accountCreateRequest = AccountCreateRequest.builder()
                .nickname("nickname1")
                .email("example1@gmail.com")
                .password("1234")
                .build();

        String encodedPassword = passwordEncoder.encode(accountCreateRequest.getPassword());
        Account account = accountCreateRequest.toEntity(encodedPassword);
        accountRepository.save(account);

        AccountRequest accountRequest = AccountRequest.builder()
                .email("example1@gmail.com")
                .password("1234")
                .build();
        String json = objectMapper.writeValueAsString(accountRequest);
        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(csrf())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithAccount
    @DisplayName("프로필 수정테스트 성공 케이스")
    void updateProfile_success() throws Exception {
        // given
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RestUserDetails principal = (RestUserDetails) authentication.getPrincipal();

        AccountEditRequest accountEditRequest = AccountEditRequest.builder()
                .bio("bio입니다.")
                .url("www.test.com")
                .occupation("occupation입니다.")
                .location("location입니다.")
                .build();

        String json = objectMapper.writeValueAsString(accountEditRequest);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.patch("/profile/{accountId}", principal.getAccountResponse().getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.profileResponse.bio").value("bio입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.profileResponse.url").value("www.test.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.profileResponse.occupation").value("occupation입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.profileResponse.location").value("location입니다."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithAccount
    @DisplayName("프로필 수정테스트 실패 케이스(입력값 오류)")
    void updateProfile_fail() throws Exception {
        // given
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RestUserDetails principal = (RestUserDetails) authentication.getPrincipal();

        AccountEditRequest accountEditRequest = AccountEditRequest.builder()
                .bio("50글자를초과하는입력12345678910123456789101234567891012345678910123456789101234567891012345678910123456789101234567891012345678910.")
                .url("www.test.com")
                .occupation("occupation입니다.")
                .location("location입니다")
                .build();

        String json = objectMapper.writeValueAsString(accountEditRequest);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.patch("/profile/{accountId}", principal.getAccountResponse().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithAccount
    @DisplayName("패스워드 수정 테스트 성공 케이스")
    void updatePassword_success() throws Exception {
        // given
        // TODO  액세스토큰 인증했다고 가정, 액세스토큰을 통해 얻은 사용자 정보와 수정하려는 id가 동일한지 확인

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RestUserDetails principal = (RestUserDetails) authentication.getPrincipal();

        PasswordEditRequest passwordEditRequest = PasswordEditRequest.builder()
                .newPassword("new1234")
                .newPasswordConfirm("new1234")
                .build();

        String json = objectMapper.writeValueAsString(passwordEditRequest);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.patch("/password/{accountId}", principal.getAccountResponse().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithAccount
    @DisplayName("패스워드 수정 테스트 실패케이스 (입력값 불일치) ")
    void updatePassword_fail() throws Exception {
        // given
        // TODO  액세스토큰 인증했다고 가정, 액세스토큰을 통해 얻은 사용자 정보와 수정하려는 id가 동일한지 확인

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RestUserDetails principal = (RestUserDetails) authentication.getPrincipal();

        PasswordEditRequest passwordEditRequest = PasswordEditRequest.builder()
                .newPassword("new1234")
                .newPasswordConfirm("new12345")
                .build();

        String json = objectMapper.writeValueAsString(passwordEditRequest);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.patch("/password/{accountId}", principal.getAccountResponse().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithAccount
    @DisplayName("알림 수정 테스트 성공케이스")
    void updateNotification() throws Exception {
        // given
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RestUserDetails principal = (RestUserDetails) authentication.getPrincipal();

        NotificationEditRequest notificationEditRequest = NotificationEditRequest.builder()
                .studyCreatedByEmail(true)
                .studyCreatedByWeb(false)
                .studyUpdatedByEmail(true)
                .studyUpdatedByWeb(false)
                .studyEnrollmentResultByEmail(true)
                .studyEnrollmentResultByWeb(false)
                .build();

        String json = objectMapper.writeValueAsString(notificationEditRequest);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.patch("/notification/{accountId}", principal.getAccountResponse().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.."))
                .andDo(MockMvcResultHandlers.print());

        // then
    }
}