package study.studymatching_backend.account.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import study.studymatching_backend.domain.Account;

@Getter
@Setter
public class AccountEditRequest {

    private String bio;
    private String url;
    private String occupation;
    private String location;

    @Builder
    public AccountEditRequest(String bio, String url, String occupation, String location) {
        this.bio = bio;
        this.url = url;
        this.occupation = occupation;
        this.location = location;
    }

    // dto가 단순 데이터전달 역할을 넘어서고 있음
    // 엔티티가 자신의 상태 변경에 대한 책임을 지는것이 바람직함
    // 엔티티가 dto에 의존하지 않도록 하려다보니 변경메서드를 dto에 놓게되버림..
    // 세 가지를 다 만족시키는건 힘들듯..
//    public Account UpdateEntity(Account account) {
//        Account.AccountBuilder builder = account.toBuilder();
//
//        if (bio != null && !bio.isBlank()) {
//            builder.bio(bio);
//        }
//        if (url != null && !url.isBlank()) {
//            builder.url(url);
//        }
//        if (occupation != null && !occupation.isBlank()) {
//            builder.occupation(occupation);
//        }
//        if (location != null && !location.isBlank()) {
//            builder.location(location);
//        }
//        return builder.build();
//    }
}
