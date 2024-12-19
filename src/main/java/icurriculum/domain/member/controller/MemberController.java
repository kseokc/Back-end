package icurriculum.domain.member.controller;

import icurriculum.domain.member.Member;
import icurriculum.domain.member.Service.MemberService;
import icurriculum.domain.member.dto.MemberConverter;
import icurriculum.domain.member.dto.MemberRequest;
import icurriculum.domain.member.dto.MemberResponse;
import icurriculum.global.response.ApiResponse;
import icurriculum.global.security.annotation.LoginMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/info")
    @Operation(summary = "회원 정보 조회 api", description = "현재 로그인한 회원의 정보를 반환하는 api입니다.<br>**반환 형식**<br>이름<br>이메일<br>입학년도(ex. 19, 20, 21)<br>전공 리스트([{전공 종류, 학과}])")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", description = "성공",
            content = @Content(schema = @Schema(implementation = MemberResponse.MemberInfo.class))
    )
    public ApiResponse<MemberResponse.MemberInfo> getMemberInfo(@LoginMember Member member) {
        MemberResponse.MemberInfo memberInfo = memberService.getMemberInfo(member);

        return ApiResponse.onSuccess(memberInfo);
    }

}
