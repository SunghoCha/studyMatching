package study.studymatching_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiResponse<T> {

    private String description;

    private T data;

    @Builder
    public ApiResponse(String description, T data) {
        this.description = description;
        this.data = data;
    }
}
