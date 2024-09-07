package study.studymatching_backend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"id"})
public class Resources {

    @Id @GeneratedValue
    @Column(name = "resources_id")
    private Long id;
    private String resourceName;
    private String httpMethod;
    private int orderNum;
    private String resourceType;

    @Builder
    public Resources(String resourceName, String httpMethod, int orderNum, String resourceType) {
        this.resourceName = resourceName;
        this.httpMethod = httpMethod;
        this.orderNum = orderNum;
        this.resourceType = resourceType;
    }

    public Resources(String resourceName) {
        this.resourceName = resourceName;
    }
}
