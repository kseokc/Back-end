package icurriculum.domain.member.Controller;

import icurriculum.domain.member.Member;
import icurriculum.domain.member.Service.MemberService;
import icurriculum.domain.member.dto.MemberConverter;
import icurriculum.domain.member.dto.MemberRequest;
import icurriculum.domain.member.dto.MemberResponse;
import icurriculum.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberConverter memberConverter;

    @PostMapping("/join")
    public ApiResponse<MemberResponse.JoinResponse> join(@RequestBody @Valid MemberRequest.JoinDTO request) {
        Member member = memberService.join(request);
        MemberResponse.JoinResponse joinResponse = memberConverter.toJoinResponse(member);

        return ApiResponse.onSuccess(joinResponse);
    }
}
