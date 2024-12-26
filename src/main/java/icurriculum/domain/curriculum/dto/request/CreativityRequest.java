package icurriculum.domain.curriculum.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import icurriculum.global.valid.annotation.Code;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

public record CreativityRequest(
        @JsonProperty("인정과목")
        Set<@Code String> approvedCodeSet,

        @JsonProperty("요구학점")
        @NotNull(message = "요구 학점은 필수 값입니다.")
        Integer requiredCredit,

        @JsonProperty("추가정보")
        Map<String, String> additionalInfoMap
) {

}
