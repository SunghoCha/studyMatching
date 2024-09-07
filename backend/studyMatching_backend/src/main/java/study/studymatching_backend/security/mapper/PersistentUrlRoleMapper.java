package study.studymatching_backend.security.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import study.studymatching_backend.security.repository.RoleResourcesRepository;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PersistentUrlRoleMapper {

    private final RoleResourcesRepository roleResourcesRepository;

    public Map<String, String> getUrlRoleMappings() {
        return roleResourcesRepository.findAll().stream()
                .collect(Collectors.toMap(
                        roleResources -> roleResources.getResources().getResourceName(),
                        roleResources -> roleResources.getRole().getRoleName()
                ));
    }
}
