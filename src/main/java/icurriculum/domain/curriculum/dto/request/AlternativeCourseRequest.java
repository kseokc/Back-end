package icurriculum.domain.curriculum.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import icurriculum.global.valid.annotation.Code;
import java.util.Map;
import java.util.Set;

public record AlternativeCourseRequest(
        @JsonProperty("대체과목코드")
        Map<@Code String, Set<@Code String>> alternativeCourseCodeMap
) {

}
