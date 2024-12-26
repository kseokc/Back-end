package icurriculum.domain.curriculum.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import icurriculum.global.valid.annotation.Code;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

public record MajorRequiredRequest(
        @JsonProperty("과목코드")
        @NotNull(message = "과목 코드는 필수 값입니다.")
        Set<@Code String> codeSet,

        @JsonProperty("추가정보")
        Map<String, String> additionalInfoMap
) {

}
