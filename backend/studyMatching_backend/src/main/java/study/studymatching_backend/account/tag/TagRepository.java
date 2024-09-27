package study.studymatching_backend.account.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import study.studymatching_backend.domain.Tag;

import java.util.Set;

@Transactional(readOnly = true)
public interface TagRepository extends JpaRepository<Tag, Long> {

    Set<Tag> findByTitleIn(Set<String> titles);
}
