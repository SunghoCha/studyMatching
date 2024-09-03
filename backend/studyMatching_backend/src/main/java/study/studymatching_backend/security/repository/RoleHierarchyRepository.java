package study.studymatching_backend.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.studymatching_backend.domain.RoleHierarchy;

@Repository
public interface RoleHierarchyRepository extends JpaRepository<RoleHierarchy, Long> {
}
