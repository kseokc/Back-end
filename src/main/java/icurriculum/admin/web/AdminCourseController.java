package icurriculum.admin.web;

import icurriculum.admin.web.json.CourseDTO;
import icurriculum.domain.course.Course;
import icurriculum.domain.course.repository.CourseRepository;
import icurriculum.global.response.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
@Validated
public class AdminCourseController {

    private final CourseRepository repository;

    /*
     * Course 데이터 삽입 API
     * Front 호출 X
     */
    @PostMapping("/insert-bulk")
    public ApiResponse<Void> insertCourses(
            @RequestBody @Valid Map<String, CourseDTO> coursesDTO
    ) {
        List<Course> courses = convertToCourses(coursesDTO);
        repository.saveAll(courses);

        return ApiResponse.OK;
    }

    private List<Course> convertToCourses(Map<String, CourseDTO> coursesDTO){
        return coursesDTO.values().stream()
                .map(courseDTO -> Course.builder()
                        .credit(courseDTO.getCredit().intValue())
                        .name(courseDTO.getName())
                        .code(courseDTO.getCode())
                        .build()
                )
                .toList();
    }

}
