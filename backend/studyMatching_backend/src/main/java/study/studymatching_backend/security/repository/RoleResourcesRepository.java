package study.studymatching_backend.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.studymatching_backend.domain.RoleResources;

@Repository
public interface RoleResourcesRepository extends JpaRepository<RoleResources, Long> {
}
