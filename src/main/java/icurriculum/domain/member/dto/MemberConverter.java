package icurriculum.domain.member.dto;

import icurriculum.domain.member.Member;
import icurriculum.domain.member.RoleType;
import icurriculum.domain.membermajor.MemberMajor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberConverter {

    public Member toEntity(MemberRequest.JoinDTO request) {
        return Member.builder()
                .email(request.getEmail())
                .name(request.getName())
                .joinYear(request.getJoinYear())
                .roleType(RoleType.ROLE_USER) //회원가입은 무조건 User로만 가능
                .build();
    }

    public MemberResponse.JoinResponse toJoinResponse(Member member) {
        return MemberResponse.JoinResponse.builder()
                .email(member.getEmail())
                .join_date(member.getCreatedAt())
                .build();
    }

    public MemberResponse.MemberInfo toMemberInfo(Member member, List<MemberMajor> memberMajorList) {
        List<MemberResponse.MajorInfo> majorList = memberMajorList.stream().map(
                this::toMajorInfo
        ).toList();

        return MemberResponse.MemberInfo.builder()
                .name(member.getName())
                .email(member.getEmail())
                .joinYear(member.getJoinYear() % 100)
                .majorList(majorList)
                .build();
    }

    public MemberResponse.MajorInfo toMajorInfo(MemberMajor memberMajor) {
        return MemberResponse.MajorInfo.builder()
                .majorType(memberMajor.getMajorType())
                .departmentName(memberMajor.getDepartment().getName())
                .build();
    }
}
