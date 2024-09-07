package study.studymatching_backend.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.StringUtils;
import study.studymatching_backend.account.dto.AccountRequest;
import study.studymatching_backend.security.token.RestAuthenticationToken;

import java.io.IOException;

@Slf4j
public class RestAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private String emailParameter = "email";
    private String passwordParameter = "password";
    private boolean postOnly = true;

    public RestAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        AccountRequest accountRequest = objectMapper.readValue(request.getReader(), AccountRequest.class);
        if (!StringUtils.hasText(accountRequest.getEmail()) || !StringUtils.hasText(accountRequest.getPassword())) {
            throw new AuthenticationServiceException("Input is not provided");
        }
        RestAuthenticationToken authRequest = RestAuthenticationToken.unauthenticated(accountRequest.getEmail(), accountRequest.getPassword());

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
