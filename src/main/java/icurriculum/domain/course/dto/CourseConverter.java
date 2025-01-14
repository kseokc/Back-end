package icurriculum.domain.course.dto;

import icurriculum.domain.course.Course;
import icurriculum.domain.course.dto.CourseResponse;
import icurriculum.domain.take.Category;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class CourseConverter {

    public static CourseResponse.DetailInfoDTO toCourseDetailInfo(Course course,
        Category category) {
        return CourseResponse.DetailInfoDTO.builder()
            .courseId(course.getId())
            .name(course.getName())
            .credit(course.getCredit())
            .code(course.getCode())
            .category(category.toString())
            .build();
    }

    public static  CourseResponse.DetailInfoListDTO toCourseDetailInfoList(List<Course> courseList, Map<String, Category> judgedCodes) {
        List<CourseResponse.DetailInfoDTO> detailInfoDTOs = courseList.stream()
            .map(course -> CourseResponse.DetailInfoDTO.builder()
                .courseId(course.getId())
                .name(course.getName())
                .credit(course.getCredit())
                .code(course.getCode())
                .category(judgedCodes.getOrDefault(course.getCode(), Category.교양선택).name())
                .build())
            .collect(Collectors.toList());

        return CourseResponse.DetailInfoListDTO.builder()
            .detailInfoList(detailInfoDTOs)
            .build();
    }
}
