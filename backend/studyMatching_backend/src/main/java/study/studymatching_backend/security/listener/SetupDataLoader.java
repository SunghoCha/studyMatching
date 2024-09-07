package study.studymatching_backend.security.listener;


import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.studymatching_backend.account.repository.AccountRepository;
import study.studymatching_backend.account.repository.AccountRoleRepository;
import study.studymatching_backend.domain.*;
import study.studymatching_backend.security.repository.ResourcesRepository;
import study.studymatching_backend.security.repository.RoleRepository;
import study.studymatching_backend.security.repository.RoleResourcesRepository;
import study.studymatching_backend.security.service.RoleService;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;
    private final AccountRoleRepository accountRoleRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleResourcesRepository roleResourcesRepository;
    private final ResourcesRepository resourcesRepository;
    private final RoleService roleService;
    private boolean alreadySetup = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        setupData();
        alreadySetup = true;
    }

    @Transactional
    public void setupData() {
        setupRoles();
        createAccountIfNotExist("admin", "admin@example.com", "1234", "ROLE_ADMIN");
        createRoleResources();
    }

    private void createAccountIfNotExist(String nickname, String email, String password, String roleName) {
        Account account = accountRepository.findByNickname(nickname).orElseGet(() -> createAndSaveAccount(nickname, email, password));
        Role role = roleRepository.findByRoleName(roleName).orElseThrow(IllegalArgumentException::new);
        addRoleToAccount(account, role);
    }

    private void addRoleToAccount(Account account, Role role) {
        if (!accountRoleRepository.existsByAccountAndRole(account, role)) {
            AccountRole accountRole = AccountRole.createAccountRole(account, role);
            accountRoleRepository.save(accountRole);
        }
    }

    private Account createAndSaveAccount(String nickname, String email, String password) {
        Account account = Account.builder()
                .nickname(nickname)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();
        return accountRepository.save(account);
    }

    private void setupRoles() {
        Role admin = Role.builder()
                .roleName("ROLE_ADMIN")
                .roleDesc("관리자")
                .build();

        Role manager = Role.builder()
                .roleName("ROLE_MANAGER")
                .roleDesc("매니저")
                .build();

        Role user = Role.builder()
                .roleName("ROLE_USER")
                .roleDesc("사용자")
                .build();

        roleRepository.saveAll(List.of(admin, manager, user));
    }

    private void createRoleResources() {

        Map<String, String> urlRoleMappings = new HashMap<>();

        urlRoleMappings.put("/", "permitAll");
        urlRoleMappings.put("/css/**", "permitAll");
        urlRoleMappings.put("/js/**", "permitAll");
        urlRoleMappings.put("/images/**", "permitAll");
        urlRoleMappings.put("/favicon.*", "permitAll");
        urlRoleMappings.put("/*/icon-*", "permitAll");
        urlRoleMappings.put("/signup", "permitAll");
        urlRoleMappings.put("/login", "permitAll");
        urlRoleMappings.put("/logout", "permitAll");
        urlRoleMappings.put("/denied", "authenticated");
        urlRoleMappings.put("/user", "ROLE_USER");
        urlRoleMappings.put("/admin/**", "ROLE_ADMIN");
        urlRoleMappings.put("/manager", "ROLE_MANAGER");
        urlRoleMappings.put("/db", "hasRole('DBA')");

        List<RoleResources> list = urlRoleMappings.entrySet().stream()
                .map(entry -> {
                    Resources resources = resourcesRepository.save(new Resources(entry.getKey()));
                    Role role = roleService.createIfNotExist(entry.getValue());
                    return RoleResources.CreateRoleResources(resources, role);
                })
                .toList();
        roleResourcesRepository.saveAll(list);
    }

}
