package study.studymatching_backend.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import study.studymatching_backend.account.dto.AccountCreateRequest;
import study.studymatching_backend.account.repository.AccountRepository;
import study.studymatching_backend.domain.Account;
import study.studymatching_backend.exception.EmailNotFoundException;

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

    @BeforeEach
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

}