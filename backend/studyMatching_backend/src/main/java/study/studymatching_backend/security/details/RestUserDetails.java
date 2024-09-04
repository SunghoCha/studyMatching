package study.studymatching_backend.security.details;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import study.studymatching_backend.account.dto.AccountCreateResponse;
import study.studymatching_backend.account.dto.AccountResponse;

import java.util.Collection;
import java.util.List;

@Getter
public class RestUserDetails implements UserDetails {

    private final AccountResponse accountResponse;
    private final List<GrantedAuthority> authorities;

    @Builder
    private RestUserDetails(AccountResponse accountResponse, List<GrantedAuthority> authorities) {
        this.accountResponse = accountResponse;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return accountResponse.getPassword();
    }

    @Override
    public String getUsername() {
        return accountResponse.getEmail();
    }
}
