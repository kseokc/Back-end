package icurriculum.domain.take.service;


import icurriculum.domain.course.Course;
import icurriculum.domain.course.repository.CourseRepository;
import icurriculum.domain.member.Member;
import icurriculum.domain.membermajor.MajorType;
import icurriculum.domain.take.Category;
import icurriculum.domain.take.Grade;
import icurriculum.domain.take.Take;
import icurriculum.domain.take.dto.TakeConverter;
import icurriculum.domain.take.dto.TakeRequest;
import icurriculum.domain.take.dto.TakeRequest.TakeCreateDTO;
import icurriculum.domain.take.dto.TakeResponse;
import icurriculum.domain.take.repository.TakeRepository;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import icurriculum.global.util.TakeUtils;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TakeService {

    private final TakeRepository repository;
    private final CourseRepository courseRepository;

    public List<Take> getTakeListByMember(Member member) {
        return repository.findByMember(member);
    }

    public Page<Take> getTakeListByMember(Member member, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByMember(member, pageable);
    }

    public LinkedList<Take> getTakeListByMemberAndMajorType(Member member, MajorType majorType) {
        List<Take> TakeList = repository.findByMemberAndMajorType(member, majorType);
        return new LinkedList<>(TakeList);
    }

    @Transactional
    public TakeResponse.TakeListDTO createTakeListByMember(
            Member member,
            TakeRequest.TakeCreateListDTO takeCreateListDTO) {

        Set<String> existedCodeSet = repository.findByMember(member).stream()
                .map(Take -> Take.getEffectiveCourse().getCode())
                .filter(code -> !code.equals("CUSTOM"))
                .collect(Collectors.toSet());

        for (TakeCreateDTO takeCreateDTO : takeCreateListDTO.getTakeCreateDTOList()) {
            if (existedCodeSet.contains(takeCreateDTO.getCode())) {
                throw new GeneralException(ErrorStatus.TAKE_CODE_DUPLICATE);
            }
        }

        List<Take> takes = takeCreateListDTO.getTakeCreateDTOList().stream()
                .map(dto -> {

                    if (TakeUtils.isCustomCode(dto.getCode())) {
                        return TakeConverter.toCustomTakeEntity(member, dto);
                    } else {
                        Course course = courseRepository.findByCode(dto.getCode())
                                .orElseThrow(() -> new GeneralException(
                                        ErrorStatus.COURSE_IS_NOT_VALID));
                        return TakeConverter.toTakeEntity(member, dto, course);
                    }
                })
                .collect(Collectors.toList());

        repository.saveAll(takes);

        List<Take> takeList = repository.findByMember(member);

        return TakeConverter.toTakeList(takeList, 1);
    }


    @Transactional
    public TakeResponse.TakeListDTO updateTakeByMember(
            Member member,
            TakeRequest.TakeUpdateDTO takeUpdateDTO) {

        Take take = repository.findById(takeUpdateDTO.getTakeId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.TAKE_NOT_EXIST));

        take.updateTakeInfo(
                Category.valueOf(takeUpdateDTO.getCategory()),
                MajorType.to(takeUpdateDTO.getMajorType()),
                Grade.getGradeByScore(takeUpdateDTO.getGrade())
        );

        List<Take> takeList = repository.findByMember(member);

        return TakeConverter.toTakeList(takeList, 1);
    }


    @Transactional
    public TakeResponse.TakeListDTO deleteTakeByMember(
            Member member,
            TakeRequest.TakeDeleteDTO takeDeleteDTO) {
        repository.deleteById(takeDeleteDTO.getTakeId());

        List<Take> takeList = repository.findByMember(member);

        return TakeConverter.toTakeList(takeList, 1);
    }

}
