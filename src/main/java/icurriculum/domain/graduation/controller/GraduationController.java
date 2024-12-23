package icurriculum.domain.graduation.controller;

import icurriculum.domain.graduation.dto.GraduationResponse;
import icurriculum.domain.graduation.service.AllGraduationService;
import icurriculum.domain.member.Member;
import icurriculum.global.response.ApiResponse;
import icurriculum.global.security.annotation.LoginMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GraduationController {

    private final AllGraduationService allGraduationService;

    @PostMapping("/check-graduation")
    @Operation(
            summary = "졸업 요건 확인",
            description = "사용자의 졸업 요건 검사 확인. 응답에는 각 카테고리별 검사 결과 정보가 포함됩니다.\n\n" +
                    "응답 예시와 상세설명:\n" +
                    "<pre>{\n" +
                    "  \"isSuccess\": true, // (성공 여부)\n" +
                    "  \"code\": \"COMMON200\", // (응답 코드)\n" +
                    "  \"message\": \"성공입니다.\", // (응답 메시지)\n" +
                    "  \"result\": { // (결과 데이터)\n" +
                    "    \"swAiDTO(SWAI 결과)\": {\n" +
                    "      \"completedCredit(이수학점)\": 0,\n" +
                    "      \"requiredCredit(필요학점)\": 0,\n" +
                    "      \"isClear(성공여부)\": true\n" +
                    "    },\n" +
                    "    \"creativityDTO(창의 결과)\": {\n" +
                    "      \"completedCredit(이수학점)\": 0,\n" +
                    "      \"requiredCredit(필요학점)\": 3,\n" +
                    "      \"isClear(성공여부)\": false\n" +
                    "    },\n" +
                    "    \"coreDTO(핵심교양 결과)\": {\n" +
                    "      \"completedCredit(이수학점)\": 0,\n" +
                    "      \"requiredCredit(필요학점)\": 12,\n" +
                    "      \"uncompletedArea(미이수 영역)\": [\n" +
                    "        \"핵심교양1\",\n" +
                    "        \"핵심교양2\",\n" +
                    "        \"핵심교양4\",\n" +
                    "        \"핵심교양6\"\n" +
                    "      ],\n" +
                    "      \"isClear(성공여부)\": false\n" +
                    "    },\n" +
                    "    \"majorRequiredDTO(전공필수 결과)\": {\n" +
                    "      \"completedCredit(이수학점)\": 0,\n" +
                    "      \"requiredCredit(필요학점)\": 6,\n" +
                    "      \"uncompletedCourseList(미이수 전공필수 과목)\": [\n" +
                    "        {\n" +
                    "          \"createdAt\": \"2024-12-21T04:21:13.185364\", // (생성 시간)\n" +
                    "          \"id\": 493, // (과목 ID)\n" +
                    "          \"code\": \"CSE4205\", // (과목 코드)\n" +
                    "          \"name\": \"컴퓨터공학 종합설계\", // (과목 이름)\n" +
                    "          \"credit\": 3 // (학점)\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"createdAt\": \"2024-12-21T04:21:13.126\", // (생성 시간)\n" +
                    "          \"id\": 488, // (과목 ID)\n" +
                    "          \"code\": \"CSE1101\", // (과목 코드)\n" +
                    "          \"name\": \"객체지향프로그래밍 1\", // (과목 이름)\n" +
                    "          \"credit\": 3 // (학점)\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"isClear(성공여부)\": false\n" +
                    "    },\n" +
                    "    \"majorSelectDTO(전공선택 + 전공필수 학점 계산 결과)\": {\n" +
                    "      \"totalMajorCompletedCredit(총 전공 이수학점)\": 0,\n" +
                    "      \"totalMajorRequiredCredit(총 전공 필요학점)\": 65,\n" +
                    "      \"isClear(성공 여부)\": false\n" +
                    "    },\n" +
                    "    \"generalRequiredDTO(교양필수 결과)\": {\n" +
                    "      \"completedCredit(이수학점)\": 0,\n" +
                    "      \"requiredCredit(필요학점)\": 27,\n" +
                    "      \"uncompletedCourseSet(미이수 교양필수 과목)\": [\n" +
                    "        {\n" +
                    "          \"createdAt\": \"2024-12-23T16:02:34.950071\", // (생성 시간)\n" +
                    "          \"id\": 5611, // (과목 ID)\n" +
                    "          \"code\": \"GEB1126\", // (과목 코드)\n" +
                    "          \"name\": \"문제해결을 위한 글쓰기\", // (과목 이름)\n" +
                    "          \"credit\": 3 // (학점)\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"isClear(성공여부)\": false\n" +
                    "    },\n" +
                    "    \"totalCompletedCredit(총 이수학점)\": 0,\n" +
                    "    \"totalNeedCredit(총 필요학점)\": 130,\n" +
                    "    \"isOverTotalNeedCredit(총 필요학점 충족여부 결과)\": false\n" +
                    "  }\n" +
                    "}</pre>"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GraduationResponse.AllDTO.class),
                            examples = @ExampleObject(
                                    value = "{\n" +
                                            "  \"isSuccess\": true,\n" +
                                            "  \"code\": \"COMMON200\",\n" +
                                            "  \"message\": \"성공입니다.\",\n" +
                                            "  \"result\": {\n" +
                                            "    \"swAiDTO\": {\n" +
                                            "      \"completedCredit\": 0,\n" +
                                            "      \"requiredCredit\": 0,\n" +
                                            "      \"isClear\": true\n" +
                                            "    },\n" +
                                            "    \"creativityDTO\": {\n" +
                                            "      \"completedCredit\": 0,\n" +
                                            "      \"requiredCredit\": 3,\n" +
                                            "      \"isClear\": false\n" +
                                            "    },\n" +
                                            "    \"coreDTO\": {\n" +
                                            "      \"completedCredit\": 0,\n" +
                                            "      \"requiredCredit\": 12,\n" +
                                            "      \"uncompletedArea\": [\n" +
                                            "        \"핵심교양1\",\n" +
                                            "        \"핵심교양2\",\n" +
                                            "        \"핵심교양4\",\n" +
                                            "        \"핵심교양6\"\n" +
                                            "      ],\n" +
                                            "      \"isClear\": false\n" +
                                            "    },\n" +
                                            "    \"majorRequiredDTO\": {\n" +
                                            "      \"completedCredit\": 0,\n" +
                                            "      \"requiredCredit\": 6,\n" +
                                            "      \"uncompletedCourseList\": [\n" +
                                            "        {\n" +
                                            "          \"createdAt\": \"2024-12-21T04:21:13.185364\",\n"
                                            +
                                            "          \"id\": 493,\n" +
                                            "          \"code\": \"CSE4205\",\n" +
                                            "          \"name\": \"컴퓨터공학 종합설계\",\n" +
                                            "          \"credit\": 3\n" +
                                            "        },\n" +
                                            "        {\n" +
                                            "          \"createdAt\": \"2024-12-21T04:21:13.126\",\n"
                                            +
                                            "          \"id\": 488,\n" +
                                            "          \"code\": \"CSE1101\",\n" +
                                            "          \"name\": \"객체지향프로그래밍 1\",\n" +
                                            "          \"credit\": 3\n" +
                                            "        }\n" +
                                            "      ],\n" +
                                            "      \"isClear\": false\n" +
                                            "    },\n" +
                                            "    \"majorSelectDTO\": {\n" +
                                            "      \"totalMajorCompletedCredit\": 0,\n" +
                                            "      \"totalMajorRequiredCredit\": 65,\n" +
                                            "      \"isClear\": false\n" +
                                            "    },\n" +
                                            "    \"generalRequiredDTO\": {\n" +
                                            "      \"completedCredit\": 0,\n" +
                                            "      \"requiredCredit\": 27,\n" +
                                            "      \"uncompletedCourseSet\": [\n" +
                                            "        {\n" +
                                            "          \"createdAt\": \"2024-12-23T16:02:34.950071\",\n"
                                            +
                                            "          \"id\": 5611,\n" +
                                            "          \"code\": \"GEB1126\",\n" +
                                            "          \"name\": \"문제해결을 위한 글쓰기\",\n" +
                                            "          \"credit\": 3\n" +
                                            "        },\n" +
                                            "        {\n" +
                                            "          \"createdAt\": \"2024-12-23T16:02:33.112743\",\n"
                                            +
                                            "          \"id\": 5450,\n" +
                                            "          \"code\": \"ACE2104\",\n" +
                                            "          \"name\": \"통계학\",\n" +
                                            "          \"credit\": 3\n" +
                                            "        },\n" +
                                            "        {\n" +
                                            "          \"createdAt\": \"2024-12-23T16:02:32.71449\",\n"
                                            +
                                            "          \"id\": 5414,\n" +
                                            "          \"code\": \"GEB1108\",\n" +
                                            "          \"name\": \"의사소통 영어: 중급\",\n" +
                                            "          \"credit\": 3\n" +
                                            "        }\n" +
                                            "      ],\n" +
                                            "      \"isClear\": false\n" +
                                            "    },\n" +
                                            "    \"totalCompletedCredit\": 0,\n" +
                                            "    \"totalNeedCredit\": 130,\n" +
                                            "    \"isOverTotalNeedCredit\": false\n" +
                                            "  }\n" +
                                            "}"
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "403",
                    description = "Authorization 헤더 누락 시 403",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\n  \"timestamp\": \"2024-12-23T07:19:11.432+00:00\",\n  \"status\": 403,\n  \"error\": \"Forbidden\",\n  \"path\": \"/check-graduation\"\n}"
                            )
                    )
            )
    })

    public ApiResponse<GraduationResponse.AllDTO> checkAll(
            @LoginMember Member member
    ) {
        log.info("member:{}", member);
        return ApiResponse.onSuccess(
                allGraduationService.executeAll(member)
        );
    }
}

