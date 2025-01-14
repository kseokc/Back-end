package icurriculum.domain.course.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public abstract class CourseResponse {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailInfoDTO {

        private Long courseId;
        private String name;
        private Integer credit;
        private String code;
        private String category;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailInfoListDTO {
        private List<DetailInfoDTO> detailInfoList;
    }
}
