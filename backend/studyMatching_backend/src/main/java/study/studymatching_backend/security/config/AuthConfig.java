package study.studymatching_backend.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import study.studymatching_backend.security.service.RoleHierarchyService;

@Configuration
public class AuthConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public RoleHierarchyImpl roleHierarchy(RoleHierarchyService roleHierarchyService) {
        String allHierarchy = roleHierarchyService.findAllHierarchy();

        return RoleHierarchyImpl.fromHierarchy(allHierarchy);
    }


}
