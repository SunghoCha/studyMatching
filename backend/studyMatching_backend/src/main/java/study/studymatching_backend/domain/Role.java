package study.studymatching_backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Column(unique = true)
    private String roleDesc;

    private boolean isExpression = false;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Set<RoleResources> roleResources = new LinkedHashSet<>();

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Set<AccountRole> accountRoles = new HashSet<>();

    @Builder
    public Role(Authority authority, String roleDesc, boolean isExpression) {
        this.authority = authority;
        this.roleDesc = roleDesc;
        this.isExpression = isExpression;
    }
}
