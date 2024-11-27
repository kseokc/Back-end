package icurriculum.domain.curriculum.service;

import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.CurriculumDecider;
import icurriculum.domain.curriculum.repository.CurriculumRepository;
import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.membermajor.MajorType;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminCurriculumService {

    private static final String DUPLICATED_MESSAGE = "duplicate";
    private static final String CREATE_SUCCESS_MESSAGE = "새로운 커리큘럼 등록 성공!";


    private final CurriculumRepository repository;

    public Curriculum getCurriculum(
            String majorType,
            String departmentName,
            Integer joinYear
    ) {
        CurriculumDecider decider = convertToDecider(majorType, departmentName, joinYear);


        return repository.findByDecider(decider)
                .orElseThrow(() -> new GeneralException(ErrorStatus.CURRICULUM_NOT_FOUND));
    }

    public String create(
            String majorType,
            String departmentName,
            Integer joinYear,
            Curriculum curriculum
    ) {
        CurriculumDecider decider = convertToDecider(majorType, departmentName, joinYear);
        Optional<Curriculum> oldCurriculum = repository.findByDecider(decider);
        if (oldCurriculum.isPresent()) {
            return DUPLICATED_MESSAGE;
        }
/*        Curriculum newCurriculum = Curriculum.builder()
                .decider(decider)
                .core(curriculum.getCore())
                .creativity(curriculum.getCreativity())
                .swAi(curriculum.getSwAi())
                .generalRequired(curriculum.getGeneralRequired())
                .majorRequired(curriculum.getMajorRequired())
                .majorSelect(curriculum.getMajorSelect())
                .requiredCredit(curriculum.getRequiredCredit())
                .alternativeCourse(curriculum.getAlternativeCourse())
                .lastEditor(curriculum.getLastEditor())
                .build();*/
        repository.save(curriculum);
        return CREATE_SUCCESS_MESSAGE;
    }

    public void delete(
            String majorType,
            String departmentName,
            Integer joinYear
    ) {
        Curriculum curriculum = getCurriculum(majorType, departmentName, joinYear);
        repository.delete(curriculum);
    }

    public void update(
            String majorType,
            String departmentName,
            Integer joinYear,
            Curriculum curriculum
    ) {
        Curriculum oldCurriculum = getCurriculum(majorType, departmentName, joinYear);

        log.info("request:{}", curriculum);
        log.info("oldCurriculum:{}", oldCurriculum);
        oldCurriculum.update(curriculum);
        log.info("updatedCurriculum:{}", oldCurriculum);

        repository.save(oldCurriculum);
    }

    private CurriculumDecider convertToDecider(
            String majorType,
            String departmentName,
            Integer joinYear
    ) {
        return CurriculumDecider.builder()
                .majorType(MajorType.to(majorType))
                .departmentName(DepartmentName.to(departmentName))
                .joinYear(joinYear)
                .build();
    }

}
