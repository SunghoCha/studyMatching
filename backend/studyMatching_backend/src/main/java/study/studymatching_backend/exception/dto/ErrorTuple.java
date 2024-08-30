package study.studymatching_backend.exception.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
public class ErrorTuple {

    public String fieldName;
    public String message;

    @Builder
    public ErrorTuple(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }
}
