package study.studymatching_backend.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.studymatching_backend.domain.Resources;
import study.studymatching_backend.security.manager.CustomRestAuthorizationManager;
import study.studymatching_backend.security.repository.ResourcesRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ResourcesService {

    private final ResourcesRepository resourcesRepository;
    private final CustomRestAuthorizationManager authorizationManager;

    public Resources getResource(Long id) {
        return resourcesRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public List<Resources> getResources() {
        return resourcesRepository.findAll();
    }

    public void createResources(Resources resources) {
        resourcesRepository.save(resources);
        authorizationManager.reload();
    }

    public void deleteResources(Long id) {
        resourcesRepository.deleteById(id);
        authorizationManager.reload();
    }
}
