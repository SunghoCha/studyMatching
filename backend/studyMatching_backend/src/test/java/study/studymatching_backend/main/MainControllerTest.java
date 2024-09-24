package study.studymatching_backend.main;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import study.studymatching_backend.WithAccount;
import study.studymatching_backend.account.dto.AccountResponse;
import study.studymatching_backend.domain.AccountRole;
import study.studymatching_backend.dto.RoleResponse;
import study.studymatching_backend.security.details.RestUserDetails;
import study.studymatching_backend.security.token.RestAuthenticationToken;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("인증된 사용자로 요청시 Account 반환")
    void authorization_with_correct_authority() throws Exception {
        // given
        AccountResponse accountResponse = AccountResponse.builder()
                .nickname("testNickname")
                .email("testEmail")
                .roles(Set.of(RoleResponse.builder()
                        .roleName("ROLE_USER")
                        .roleDesc("사용자")
                        .build()))
                .build();

        RestUserDetails userDetails = RestUserDetails.builder()
                .accountResponse(accountResponse)
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .build();

        RestAuthenticationToken authenticationToken = new RestAuthenticationToken(userDetails.getAuthorities(), userDetails, null);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(securityContext);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value("testNickname"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("testEmail"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roles[0].roleName").value("ROLE_USER"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roles[0].roleDesc").value("사용자"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithAccount(email = "email")
    @DisplayName("인증된 사용자로 요청시 Account 반환")
    void authorization_with_correct_authority2() throws Exception {

        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value("testNickname"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("email"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roles[0].roleName").value("ROLE_USER"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roles[0].roleDesc").value("사용자"))
                .andDo(MockMvcResultHandlers.print());
    }
}