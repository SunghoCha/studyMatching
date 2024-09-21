package study.studymatching_backend;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import study.studymatching_backend.account.dto.AccountResponse;
import study.studymatching_backend.dto.RoleResponse;
import study.studymatching_backend.security.details.RestUserDetails;
import study.studymatching_backend.security.token.RestAuthenticationToken;

import java.util.List;
import java.util.Set;


public class WithAccountSecurityContextFactory implements WithSecurityContextFactory<WithAccount> {

    @Override
    public SecurityContext createSecurityContext(WithAccount annotation) {
        AccountResponse accountResponse = AccountResponse.builder()
                .nickname(annotation.nickname())
                .email(annotation.email())
                .roles(Set.of(RoleResponse.builder()
                        .roleName(annotation.roleName())
                        .roleDesc(annotation.roleDesc())
                        .build()))
                .build();

        RestUserDetails userDetails = RestUserDetails.builder()
                .accountResponse(accountResponse)
                .authorities(List.of(new SimpleGrantedAuthority(annotation.roleDesc())))
                .build();

        RestAuthenticationToken authenticationToken = new RestAuthenticationToken(userDetails.getAuthorities(), userDetails, null);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authenticationToken);

        return securityContext;
    }
}
