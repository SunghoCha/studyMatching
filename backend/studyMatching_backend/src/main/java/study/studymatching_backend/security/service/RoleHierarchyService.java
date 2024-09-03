package study.studymatching_backend.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.studymatching_backend.domain.RoleHierarchy;
import study.studymatching_backend.security.repository.RoleHierarchyRepository;

import java.util.Iterator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleHierarchyService {

    private final RoleHierarchyRepository roleHierarchyRepository;

    public String findAllHierarchy() {
        List<RoleHierarchy> roleHierarchies = roleHierarchyRepository.findAll();
        Iterator<RoleHierarchy> iterator = roleHierarchies.iterator();
        StringBuilder hierarchyRoleStringBuilder = new StringBuilder();

        while (iterator.hasNext()) {
            RoleHierarchy roleHierarchy = iterator.next();
            if (roleHierarchy.getParent() != null) {
                hierarchyRoleStringBuilder.append(roleHierarchy.getParent().getRoleName());
                hierarchyRoleStringBuilder.append(" > ");
                hierarchyRoleStringBuilder.append(roleHierarchy.getRoleName());
                hierarchyRoleStringBuilder.append(" \n ");
            }
        }
        return hierarchyRoleStringBuilder.toString();
    }
}
