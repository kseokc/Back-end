package icurriculum.domain.course.controller;

import icurriculum.domain.course.dto.CourseResponse.DetailInfoListDTO;
import icurriculum.domain.membermajor.MemberMajor;
import icurriculum.global.valid.annotation.Code;
import icurriculum.domain.course.dto.CourseResponse.DetailInfoDTO;
import icurriculum.domain.course.service.CourseService;
import icurriculum.domain.member.Member;
import icurriculum.domain.membermajor.MajorType;
import icurriculum.domain.membermajor.service.MemberMajorService;
import icurriculum.global.response.ApiResponse;
import icurriculum.global.security.annotation.LoginMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
@Validated
public class CourseController {

    private final CourseService courseService;
    private final MemberMajorService memberMajorService;

    /*
     * 현재는 주전공만 지원
     * 다중전공 지원하면 API 수정
     */
    @GetMapping
    @Operation(
        summary = "과목 조회 및 카테고리 판별",
        description = "학수번호를 통해 강좌 정보를 조회합니다. 올바른 학수번호 형식은 영어 3글자 + 숫자 4자리입니다. 과목 정보 및 사용자 전공에 따른 카테고리가 판별됩니다."
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = DetailInfoDTO.class),
                examples = @ExampleObject(
                    value = "{\n  \"isSuccess\": true,\n  \"code\": \"COMMON200\",\n  \"message\": \"성공입니다.\",\n  \"result\": {\n    \"courseId\": 1,\n    \"name\": \"객체지향프로그래밍 1\",\n    \"credit\": 3,\n    \"code\": \"CSE1101\",\n    \"category\": \"전공필수\"\n  }\n}"))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "올바르지 않은 형식의 학수번호 요청",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = "{\n  \"isSuccess\": false,\n  \"code\": \"COMMON400\",\n  \"message\": \"잘못된 요청입니다.\",\n  \"result\": [\n    \"getCourse.code: 학수번호의 형식에 맞지 않습니다. (e.g. ABC1234)\"\n  ]\n}"))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "DB에 존재하지 않는 학수번호 요청",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = "{\n  \"isSuccess\": false,\n  \"code\": \"COURSE400\",\n  \"message\": \"존재하지 않는 course 입니다.\",\n  \"result\": {}\n}"))
        )
    })
    public ApiResponse<DetailInfoDTO> getCourse(
        @LoginMember Member member,
        @RequestParam String code
    ) {
        MajorType majorType = MajorType.주전공;
        MemberMajor memberMajor = memberMajorService
            .getMemberMajorByMemberAndMajorType(member, majorType);
        DetailInfoDTO course = courseService.getCourse(code, memberMajor);

        return ApiResponse.onSuccess(course);
    }

    @GetMapping("/all")
    @Operation(
        summary = "과목 조회 및 카테고리 판별",
        description = "학수번호를 통해 강좌 정보를 조회합니다. 올바른 학수번호 형식은 영어 3글자 + 숫자 4자리입니다. 과목 정보 및 사용자 전공에 따른 카테고리가 판별됩니다."
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = DetailInfoDTO.class),
                examples = @ExampleObject(
                    value = "{\n"
                        + "  \"isSuccess\": true,\n"
                        + "  \"code\": \"COMMON200\",\n"
                        + "  \"message\": \"성공입니다.\",\n"
                        + "  \"result\": {\n"
                        + "    \"detailInfoList\": [\n"
                        + "      {\n"
                        + "        \"courseId\": 1,\n"
                        + "        \"name\": \"객체지향프로그래밍 1\",\n"
                        + "        \"credit\": 3,\n"
                        + "        \"code\": \"CSE1101\",\n"
                        + "        \"category\": \"전공필수\"\n"
                        + "      }\n"
                        + "    ]\n"
                        + "  }\n"
                        + "}"))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "올바르지 않은 형식의 학수번호 요청",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = "{\n  \"isSuccess\": false,\n  \"code\": \"COMMON400\",\n  \"message\": \"잘못된 요청입니다.\",\n  \"result\": [\n    \"getCourse.code: 학수번호의 형식에 맞지 않습니다. (e.g. ABC1234)\"\n  ]\n}"))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "DB에 존재하지 않는 학수번호 요청",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = "{\n  \"isSuccess\": false,\n  \"code\": \"COURSE400\",\n  \"message\": \"존재하지 않는 course 입니다.\",\n  \"result\": {}\n}"))
        )
    })
    public ApiResponse<DetailInfoListDTO> getCourses(
        @LoginMember Member member,
        @RequestParam String code
    ){
        MajorType majorType = MajorType.주전공;
        MemberMajor memberMajor = memberMajorService
            .getMemberMajorByMemberAndMajorType(member, majorType);
        DetailInfoListDTO courses = courseService.getCourses(code, memberMajor);

        return ApiResponse.onSuccess(courses);
    }


}