package study.studymatching_backend.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import study.studymatching_backend.security.mapper.PersistentUrlRoleMapper;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthorizationService {

    private final PersistentUrlRoleMapper delegate;

    public Map<String, String> getUrlRoleMappings() {
        return delegate.getUrlRoleMappings();
    }
}
