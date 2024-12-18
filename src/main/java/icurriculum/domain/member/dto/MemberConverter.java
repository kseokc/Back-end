package icurriculum.domain.member.dto;

import icurriculum.domain.member.Member;
import icurriculum.domain.member.RoleType;
import org.springframework.stereotype.Component;

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
}
