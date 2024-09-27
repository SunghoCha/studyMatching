package study.studymatching_backend.account.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import study.studymatching_backend.domain.Tag;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter @Setter
@NoArgsConstructor
public class TagEditRequest {

    private Set<String> tags;

    @Builder
    private TagEditRequest(Set<String> tags) {
        this.tags = tags;
    }

    public Set<Tag> toEntity() {
        return tags.stream().map(tag -> Tag.builder().title(tag).build()).collect(Collectors.toSet());
    }
}
