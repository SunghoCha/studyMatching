package study.studymatching_backend.account.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import study.studymatching_backend.domain.AccountTag;

import java.util.Set;
import java.util.stream.Collectors;

@Getter @Setter
public class TagResponse {

    Set<String> tags;

    @Builder
    private TagResponse(Set<String> tags) {
        this.tags = tags;
    }

    public static TagResponse of(Set<AccountTag> accountTags) {
        Set<String> tags = accountTags.stream()
                .map(accountTag -> accountTag.getTag().getTitle())
                .collect(Collectors.toSet());

        return TagResponse.builder()
                .tags(tags)
                .build();
    }
}
