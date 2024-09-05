package study.studymatching_backend.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import study.studymatching_backend.security.filter.RestAuthenticationFilter;
import study.studymatching_backend.security.manager.CustomRestAuthorizationManager;
import study.studymatching_backend.security.provider.RestAuthenticationProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomRestAuthorizationManager restAuthorizationManager;
    private final RestAuthenticationProvider restAuthenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/sign-up", "/check-email", "/check-email-token",
                                "/emali-login", "/check-email-login", "/login-link", "/profile/*").permitAll()
                        .requestMatchers("/css/**", "/images/**", "/js/**", "/favicon.*", "/*/icon-*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/profile/*").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain restSecurityFilterChain(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(restAuthenticationProvider);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        RestAuthenticationFilter authenticationFilter = new RestAuthenticationFilter("/api/login");
        authenticationFilter.setAuthenticationManager(authenticationManager);

        http
                .securityMatcher("/api/**")
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().access(restAuthorizationManager)
                )
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
