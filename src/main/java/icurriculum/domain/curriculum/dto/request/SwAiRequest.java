package icurriculum.domain.curriculum.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import icurriculum.global.valid.annotation.Code;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

public record SwAiRequest(
        @JsonProperty("인정과목")
        Set<@Code String> approvedCodeSet,

        @JsonProperty("영역대체과목")
        Set<@Code String> areaAlternativeCodeSet,

        @JsonProperty("필요학점")
        @NotNull(message = "필요 학점은 필수 값입니다.")
        Integer requiredCredit,

        @JsonProperty("추가정보")
        Map<String, String> additionalInfoMap
) {

}
