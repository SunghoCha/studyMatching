package study.studymatching_backend;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithAccountSecurityContextFactory.class)
public @interface WithAccount {

    String password() default "1234";

    String nickname() default "testNickname";

    String email() default "testEmail";

    String roleName() default "ROLE_USER";

    String roleDesc() default "사용자";
}
