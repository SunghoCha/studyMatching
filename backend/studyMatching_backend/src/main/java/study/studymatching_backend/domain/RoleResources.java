package study.studymatching_backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoleResources {

    @Id @GeneratedValue
    @Column(name = "role_resources_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resources_id")
    private Resources resources;

    public RoleResources(Resources resources, Role role) {
        this.resources = resources;
        this.role = role;
    }

    public static RoleResources CreateRoleResources(Resources resources, Role role) {
        return new RoleResources(resources, role);
    }
}
