package study.studymatching_backend.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.studymatching_backend.domain.Role;
import study.studymatching_backend.security.repository.RoleRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role createIfNotExist(String roleName) {
        Role role = roleRepository.findByRoleName(roleName).orElseGet(()-> new Role(roleName));
        roleRepository.save(role);
        return role;
    }
}
