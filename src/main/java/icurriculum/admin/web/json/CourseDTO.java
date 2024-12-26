package icurriculum.admin.web.json;

import icurriculum.global.valid.annotation.Code;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CourseDTO {

    @Code
    private String code;

    @NotBlank
    private String name;

    @Min(1)
    @Max(10)
    private Double credit;

}
