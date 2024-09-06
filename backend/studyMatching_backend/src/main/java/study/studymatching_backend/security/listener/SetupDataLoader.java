package study.studymatching_backend.security.listener;


import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.studymatching_backend.account.repository.AccountRepository;
import study.studymatching_backend.account.repository.AccountRoleRepository;
import study.studymatching_backend.domain.Account;
import study.studymatching_backend.domain.AccountRole;
import study.studymatching_backend.domain.Authority;
import study.studymatching_backend.domain.Role;
import study.studymatching_backend.security.repository.RoleRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SetupDataLoader  {

//    private final RoleRepository roleRepository;
//    private final AccountRoleRepository accountRoleRepository;
//    private final AccountRepository accountRepository;
//    private final PasswordEncoder passwordEncoder;
//    private boolean alreadySetup = false;
//
//    @Override
//    public void onApplicationEvent(ApplicationEvent event) {
//        if (alreadySetup) {
//            return;
//        }
//        setupData();
//        alreadySetup = true;
//    }
//
//    @Transactional
//    public void setupData() {
//        setupRoles();
//        createAccountIfNotExist("admin", "admin@example.com", "1234", Authority.ADMIN);
//    }
//
//    private void createAccountIfNotExist(String nickname, String email, String password, Authority authority) {
//        Account account = accountRepository.findByNickname(nickname).orElseGet(() -> createAndSaveAccount(nickname, email, password));
//        Role role = roleRepository.findByAuthority(authority).orElseThrow(IllegalArgumentException::new);
//        addRoleToAccount(account, role);
//    }
//
//    private void addRoleToAccount(Account account, Role role) {
//        if (!accountRoleRepository.existsByAccountAndRole(account, role)) {
//            AccountRole accountRole = AccountRole.createAccountRole(account, role);
//            accountRoleRepository.save(accountRole);
//        }
//    }
//
//    private Account createAndSaveAccount(String nickname, String email, String password) {
//        Account account = Account.builder()
//                .nickname(nickname)
//                .email(email)
//                .password(passwordEncoder.encode(password))
//                .build();
//        return accountRepository.save(account);
//    }
//
//    private void setupRoles() {
//        Role admin = Role.builder()
//                .authority(Authority.ADMIN)
//                .roleDesc("관리자")
//                .build();
//
//        Role manager = Role.builder()
//                .authority(Authority.MANAGER)
//                .roleDesc("매니저")
//                .build();
//
//        Role user = Role.builder()
//                .authority(Authority.USER)
//                .roleDesc("사용자")
//                .build();
//
//        roleRepository.saveAll(List.of(admin, manager, user));
//    }
}
