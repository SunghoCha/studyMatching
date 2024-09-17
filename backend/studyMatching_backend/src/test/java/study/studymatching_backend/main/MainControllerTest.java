package study.studymatching_backend.main;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import study.studymatching_backend.account.dto.AccountResponse;
import study.studymatching_backend.domain.AccountRole;
import study.studymatching_backend.dto.RoleResponse;
import study.studymatching_backend.security.details.RestUserDetails;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    @DisplayName("인증된 사용자로 요청시 Account 반환")
    void authorization_with_correct_authority() throws Exception {
        AccountResponse accountResponse = AccountResponse.builder()
                .nickname("testNickname")
                .email("testEmail")
                .roles(Set.of(RoleResponse.builder()
                        .roleName("ROLE_USER")
                        .roleDesc("사용자")
                        .build()))
                .build();

        RestUserDetails.builder()
                .accountResponse(accountResponse)
                .authorities()
                .build();

        // given
        mockMvc.perform(MockMvcRequestBuilders.get("/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                // .andExpect(MockMvcResultMatchers.jsonPath())
                .andDo(MockMvcResultHandlers.print());
        // when

        // then
    }
}