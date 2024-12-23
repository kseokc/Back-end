package icurriculum.domain.curriculum.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import icurriculum.global.valid.annotation.Code;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

public record CoreRequest(
        @JsonProperty("영역_지정여부")
        @NotNull(message = "영역 지정 여부는 필수 값입니다.")
        Boolean isAreaFixed,

        @JsonProperty("요구학점")
        @NotNull(message = "요구 학점은 필수 값입니다.")
        Integer requiredCredit,

        @JsonProperty("필수영역")
        Set<String> requiredAreaSet,

        @JsonProperty("영역별_지정과목")
        Map<String, Set<@Code String>> areaDeterminedCodeMap,

        @JsonProperty("영역별_대체과목")
        Map<String, Set<@Code String>> areaAlternativeCodeMap,

        @JsonProperty("추가정보")
        Map<String, String> additionalInfoMap

) {

}
