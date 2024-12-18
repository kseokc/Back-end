package icurriculum.global.response.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorStatus {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "로그인 인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    /*
     * mail
     */
    MAIL_NOT_FOUND(HttpStatus.BAD_REQUEST, "MAIL400","잘못된 메일입니다."),
    MAIL_NOT_SEND(HttpStatus.BAD_REQUEST, "MAIL401", "메일 전송에 실패했습니다."),
    /*
     * take
     */
    TAKE_HAS_ABNORMAL_COURSE(HttpStatus.BAD_REQUEST, "TAKE401",
        "Take에는 Course 또는 CustomCourse 중 하나만 채워져 있어야 한다"),
    TAKE_CODE_DUPLICATE(HttpStatus.BAD_REQUEST, "TAKE402",
        "Take에 이미 존재하는 수업입니다."),
    TAKE_NOT_EXIST(HttpStatus.BAD_REQUEST, "TAKE403",
        "Take에 존재하지 않는 수업입니다."),

    /*
     * memberMajor
     */
    MEMBER_MAJOR_NOT_FOUND(HttpStatus.BAD_REQUEST, "MAJOR401", "회원의 전공이 존재하지 않습니다."),

    /*
     * graduation
     */
    MAJOR_TYPE_NOT_FOUND(HttpStatus.BAD_REQUEST, "GRADUATION401", "지원하지 않는 전공타입입니다."),

    /*
     * curriculum
     */
    CURRICULUM_NOT_FOUND(HttpStatus.BAD_REQUEST, "CURRICULUM401", "해당학과의 졸업요건을 지원하지 않습니다."),

    CURRICULUM_NOT_VALID_CATEGORY(HttpStatus.BAD_REQUEST, "CURRICULUM402",
            "CurriculumCode 에는 전공필수, 전공선택, 교양필수 조회가능합니다."),
    CORE_NOT_VALID_CATEGORY(HttpStatus.BAD_REQUEST, "CURRICULUM403",
            "Core는 핵심교양만 조회가능합니다."),
    CORE_INVALID_DATA(HttpStatus.BAD_REQUEST, "CURRICULUM404",
            "Core의 데이터형식이 올바르지 않습니다."),
    CURRICULUM_MISSING_VALUE(HttpStatus.BAD_REQUEST, "CURRICULUM405",
            "Curriculum 내부의 필수값이 빠졌습니다."),
    CURRICULUM_DECIDER_MISSING_VALUE(HttpStatus.BAD_REQUEST, "CURRICULUM406",
            "CurriculumDecider 내부의 필수값이 빠졌습니다."),
    SW_AI_INVALID_DATA(HttpStatus.BAD_REQUEST, "CURRICULUM407",
            "sw_ai의 데이터형식이 올바르지 않습니다."),
    CREATIVITY_INVALID_DATA(HttpStatus.BAD_REQUEST, "CURRICULUM408",
            "창의의 데이터형식이 올바르지 않습니다."),

    /*
     * majorType
     */
    MAJOR_TYPE_INVALID_DATA(HttpStatus.BAD_REQUEST, "MAJORTYPE400", "전공상태의 데이터형식이 올바르지 않습니다."),

    /*
     * departmentName
     */
    DEPARTMENT_NAME_INVALID_DATA(HttpStatus.BAD_REQUEST, "DEPARTMENTNAME400",
            "학과이름의 데이터형식이 올바르지 않습니다."),
    DEPARTMENT_NOT_FOUND_BY_NAME(HttpStatus.BAD_REQUEST, "DEPARTMENT400", "해당 이름의 학과가 존재하지 않습니다."),

    /*
     * category
     */
    CATEGORY_IS_NOT_VALID(HttpStatus.BAD_REQUEST, "CATEGORY401", "사용자가 수정한 과목영역이 올바르지 않습니다."),
    CATEGORY_INVALID_DATA(HttpStatus.BAD_REQUEST, "CATEGORY402", "과목영역의 데이터형식이 올바르지 않습니다."),

    /*
     * processor
     */
    PROCESSOR_DATA_EXCEPTION(HttpStatus.BAD_REQUEST, "PROCESSOR401",
            "졸업요건 프로세서에 전달된 데이터 형식에 문제가 있습니다."),

    PROCESSOR_FIND_EXCEPTION(HttpStatus.BAD_REQUEST, "PROCESSOR402",
            "졸업요건 프로세서가 조회되지 않습니다."),


    /*
     * course
     */
    COURSE_IS_NOT_VALID(HttpStatus.BAD_REQUEST, "COURSE400", "존재하지 않는 course 입니다."),

    /*
     * code
     */
    CODE_IS_NOT_VALID(HttpStatus.BAD_REQUEST, "CODE400",
            "입력 값이 학수번호의 형식에 맞지 않습니다. (e.g., ABC1234)"),

    /*
     * additionalInfoMap
     */
    DESERIALIZATION_FAILURE(HttpStatus.BAD_REQUEST, "ADDITIONAL400",
            "추가정보 역직렬화에 실패하였습니다."),
    EMPTY_ADDITIONAL_INFO_FAILURE(HttpStatus.BAD_REQUEST, "ADDITIONAL401",
            "추가정보에 key 값에 대응하는 value 값이 존재하지 않습니다."),

    /*
     * member
     */
    MEMBER_NOT_FOUND_BY_MEMBER_ID(HttpStatus.BAD_REQUEST, "MEMBER4000",
            "해당 id를 가진 회원이 존재하지 않습니다."),
    MEMBER_NOT_FOUND_BY_EMAIL(HttpStatus.BAD_REQUEST, "MEMBER4001",
            "해당 email을 가진 회원이 존재하지 않습니다."),

    /*
     * login용 token
     */
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "JWT4000",
            "유효하지 않은 토큰입니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "JWT4001",
            "해당 refresh token이 존재하지 않습니다."),
    TOKEN_IS_EXPIRED(HttpStatus.BAD_REQUEST, "JWT4002",
            "만료된 토큰입니다."),
    AUTHENTICATION_TYPE_IS_NOT_BEARER(HttpStatus.BAD_REQUEST, "JWT4003",
            "잘못된 토큰 타입입니다."),
    ;
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
