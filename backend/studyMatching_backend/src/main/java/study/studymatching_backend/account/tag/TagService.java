package study.studymatching_backend.account.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.studymatching_backend.account.dto.TagResponse;
import study.studymatching_backend.domain.Tag;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public TagResponse getTags() {
        Set<String> tags = tagRepository.findAll().stream()
                .map(Tag::getTitle)
                .collect(Collectors.toSet());

        return TagResponse.builder()
                .tags(tags)
                .build();
    }
}
