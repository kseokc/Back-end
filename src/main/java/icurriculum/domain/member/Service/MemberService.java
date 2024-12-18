package icurriculum.domain.member.Service;

import icurriculum.domain.department.Department;
import icurriculum.domain.department.repository.DepartmentRepository;
import icurriculum.domain.member.Member;
import icurriculum.domain.member.dto.MemberConverter;
import icurriculum.domain.member.dto.MemberRequest;
import icurriculum.domain.member.repository.MemberRepository;
import icurriculum.domain.membermajor.MajorType;
import icurriculum.domain.membermajor.MemberMajor;
import icurriculum.domain.membermajor.MemberMajorConverter;
import icurriculum.domain.membermajor.repository.MemberMajorRepository;
import icurriculum.global.response.exception.GeneralException;
import icurriculum.global.response.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;
    private final MemberMajorRepository memberMajorRepository;
    private final MemberConverter memberConverter;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberMajorConverter memberMajorConverter;

    @Value("${jwt.access-token-validity-in-seconds}")
    private Long ACCESS_TOKEN_VALIDITY_IN_SECONDS;
    @Value("${jwt.refresh-token-validity-in-seconds}")
    private Long REFRESH_TOKEN_VALIDITY_IN_SECONDS;


    @Transactional
    public Member join(MemberRequest.JoinDTO request) {
        Optional.of(request.getEmail())
                .filter(email -> !memberRepository.existsByEmail(email))
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_DUPLICATE_BY_EMAIL));

        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(
                () -> new GeneralException(ErrorStatus.DEPARTMENT_NOT_FOUND)
        );
        Member newMember = memberConverter.toEntity(request);
        newMember.setPassword(bCryptPasswordEncoder.encode(request.getPassword().toLowerCase()));
        Member member = memberRepository.save(newMember);

        MemberMajor memberMajor = memberMajorConverter.toEntity(department, member);
        memberMajorRepository.save(memberMajor);
        return member;
    }

    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND_BY_MEMBER_ID)
        );
    }
    
    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND_BY_EMAIL)
        );
    }

}
