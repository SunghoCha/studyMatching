package study.studymatching_backend.dto;

import lombok.Builder;
import lombok.Getter;
import study.studymatching_backend.domain.AccountRole;
import study.studymatching_backend.domain.Authority;

@Getter
public class RoleResponse {

    private final String roleName;
    private final String roleDesc;

    @Builder
    public RoleResponse(String roleName, String roleDesc) {
        this.roleName = roleName;
        this.roleDesc = roleDesc;
    }

    public static RoleResponse of(AccountRole accountRole) {
        return RoleResponse.builder()
                .roleName(accountRole.getRole().getRoleName())
                .roleDesc(accountRole.getRole().getRoleDesc())
                .build();
    }
}
