package study.studymatching_backend.security.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import study.studymatching_backend.security.details.RestUserDetails;
import study.studymatching_backend.security.token.RestAuthenticationToken;

@Component
@RequiredArgsConstructor
public class RestAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        RestUserDetails userDetails = (RestUserDetails) userDetailsService.loadUserByUsername(email);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        return new RestAuthenticationToken(userDetails.getAuthorities(), userDetails.getAccountResponse(), null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(RestAuthenticationToken.class);
    }
}
