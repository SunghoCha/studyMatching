package study.studymatching_backend;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.studymatching_backend.account.dto.AccountCreateRequest;
import study.studymatching_backend.security.service.RestUserDetailsService;
import study.studymatching_backend.security.token.RestAuthenticationToken;

@RequiredArgsConstructor
public class WithAccountSecurityContextFactory implements WithSecurityContextFactory<WithAccount> {

    private final RestUserDetailsService userDetailsService;

    @Override
    public SecurityContext createSecurityContext(WithAccount annotation) {

        AccountCreateRequest accountCreateRequest = AccountCreateRequest.builder()
                .nickname(annotation.nickname())
                .email(annotation.email())
                .password(annotation.password())
                .build();

        userDetailsService.signup(accountCreateRequest);
        UserDetails userDetails = userDetailsService.loadUserByUsername(annotation.email());

        RestAuthenticationToken authenticationToken = new RestAuthenticationToken(userDetails.getAuthorities(), userDetails, userDetails.getPassword());
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authenticationToken);

        return securityContext;
    }
}
