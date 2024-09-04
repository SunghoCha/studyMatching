package study.studymatching_backend.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import study.studymatching_backend.account.dto.AccountResponse;
import study.studymatching_backend.account.repository.AccountRepository;
import study.studymatching_backend.account.repository.AccountRoleRepository;
import study.studymatching_backend.account.service.AccountService;
import study.studymatching_backend.domain.Account;
import study.studymatching_backend.security.details.RestUserDetails;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormUserDetailsService implements UserDetailsService {

    private final AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Account account = accountService.getByEmail(email);
        AccountResponse response = AccountResponse.of(account);
        List<GrantedAuthority> authorities = account.getAuthorities();

        return RestUserDetails.builder()
                .accountResponse(response)
                .authorities(authorities)
                .build();
    }
}
