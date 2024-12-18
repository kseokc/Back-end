package icurriculum.domain.membermajor;

import icurriculum.domain.department.Department;
import icurriculum.domain.member.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMajorConverter {

    public MemberMajor toEntity(Department department, Member member) {
        return MemberMajor.builder()
                .department(department)
                .member(member)
                .majorType(MajorType.주전공) // 주전공만 고려하는 경우
                .build();
    }
}
