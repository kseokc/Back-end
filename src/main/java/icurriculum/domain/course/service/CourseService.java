package icurriculum.domain.course.service;

import icurriculum.domain.categoryjudge.CategoryJudgeUtils;
import icurriculum.domain.course.Course;
import icurriculum.domain.course.dto.CourseConverter;
import icurriculum.domain.course.dto.CourseResponse.DetailInfoDTO;
import icurriculum.domain.course.dto.CourseResponse.DetailInfoListDTO;
import icurriculum.domain.course.repository.CourseRepository;
import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.service.CurriculumService;
import icurriculum.domain.membermajor.MemberMajor;
import icurriculum.domain.take.Category;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository repository;
    private final CurriculumService curriculumService;

    public List<Course> getCourseListByCodeSet(Set<String> codes) {
        return repository.findByCodeSet(codes);
    }

    public DetailInfoDTO getCourse(String code, MemberMajor memberMajor) {
        Course findCourse = repository.findByCode(code)
            .orElseThrow(() -> new GeneralException(ErrorStatus.COURSE_IS_NOT_VALID, this));
        Curriculum curriculum = curriculumService.getCurriculumByMemberMajor(memberMajor);

        Map<String, Category> judgedCodes = CategoryJudgeUtils.judge(code, curriculum);
        return CourseConverter.toCourseDetailInfo(findCourse, judgedCodes.get(code));
    }

    public DetailInfoListDTO getCourses(String code, MemberMajor memberMajor){
        List<Course> findCourses = repository.findByCodeToSet(code)
            .orElseThrow(()->new GeneralException(ErrorStatus.CODE_IS_NOT_VALID,this));
        Curriculum curriculum = curriculumService.getCurriculumByMemberMajor(
            memberMajor);

        List<String> codes = findCourses.stream()
            .map(Course::getCode)
            .collect(Collectors.toList());

        Map<String, Category> judgedCodes = CategoryJudgeUtils.judges(codes, curriculum);

        return CourseConverter.toCourseDetailInfoList(findCourses, judgedCodes);
    }
}
