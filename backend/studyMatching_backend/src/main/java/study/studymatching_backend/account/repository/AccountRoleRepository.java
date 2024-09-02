package study.studymatching_backend.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.studymatching_backend.domain.Account;
import study.studymatching_backend.domain.AccountRole;
import study.studymatching_backend.domain.Role;

@Repository
public interface AccountRoleRepository extends JpaRepository<AccountRole, Long> {
    boolean existsByAccountAndRole(Account account, Role role);
}
