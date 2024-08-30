package study.studymatching_backend.account;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import study.studymatching_backend.account.dto.AccountCreateRequest;
import study.studymatching_backend.account.repository.AccountRepository;
import study.studymatching_backend.domain.Account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

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
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"))
                .andDo(MockMvcResultHandlers.print());

        // then
        Account findAccount = accountRepository.findByEmail(request.getEmail());
        assertThat(findAccount.getEmail()).isEqualTo(request.getEmail());
        assertThat(findAccount.getNickname()).isEqualTo(request.getNickname());

        then(javaMailSender).should().send(ArgumentMatchers.any(SimpleMailMessage.class));
    }

}