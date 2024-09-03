package study.studymatching_backend.dto;

import lombok.Builder;
import lombok.Getter;
import study.studymatching_backend.domain.AccountRole;
import study.studymatching_backend.domain.Authority;

@Getter
public class RoleResponse {

    private final Authority authority;
    private final String roleDesc;

    @Builder
    public RoleResponse(Authority authority, String roleDesc) {
        this.authority = authority;
        this.roleDesc = roleDesc;
    }

    public static RoleResponse of(AccountRole accountRole) {
        return RoleResponse.builder()
                .authority(accountRole.getRole().getAuthority())
                .roleDesc(accountRole.getRole().getRoleDesc())
                .build();
    }
}
