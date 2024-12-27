package icurriculum.domain.take.controller;


import icurriculum.domain.member.Member;
import icurriculum.domain.member.dto.MemberResponse;
import icurriculum.domain.take.Take;
import icurriculum.domain.take.dto.TakeConverter;
import icurriculum.domain.take.dto.TakeRequest;
import icurriculum.domain.take.dto.TakeRequest.TakeCreateListDTO;
import icurriculum.domain.take.dto.TakeResponse;
import icurriculum.domain.take.dto.TakeResponse.TakeListDTO;
import icurriculum.domain.take.service.TakeService;
import icurriculum.global.response.ApiResponse;
import icurriculum.global.security.annotation.LoginMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/take")
public class TakeController {

    private final TakeService takeService;


    @Operation(summary = "수강 정보 조회 api", description = "현재 회원의 수강정보를 반환하는 api입니다.<br>**반환 형식(리스트)**<br>과목ID<br>학수번호<br>과목명<br>과목영역<br>점수<br>전공상태")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", description = "성공",
            content = @Content(schema = @Schema(implementation = TakeResponse.TakeListDTO.class))
    )
    @GetMapping("/")
    public ApiResponse<TakeListDTO> getTake(Member member,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Take> takePage = takeService.getTakeListByMember(member, page, size);
        TakeListDTO takes = TakeConverter.toTakeList(takePage.getContent(),
                takePage.getTotalPages());

        return ApiResponse.onSuccess(takes);
    }

    @Operation(summary = "수강 과목 신청 api", description = "현재 회원의 수강정보를 추가하는 api입니다.<br>**반환 형식(리스트)**<br>학수번호<br>과목명<br>과목영역<br>과목학점<br>점수<br>전공상태")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", description = "성공",
            content = @Content(schema = @Schema(implementation = TakeResponse.TakeListDTO.class))
    )
    @PostMapping("/create")
    public ApiResponse<TakeListDTO> createTake(
            @LoginMember Member member,
            @RequestBody TakeRequest.TakeCreateListDTO takeCreateListDTO) {

        TakeListDTO takes = takeService.createTakeListByMember(member,
                takeCreateListDTO);

        return ApiResponse.onSuccess(takes);
    }

    @Operation(summary = "수강 정보 수정 api", description = "현재 회원의 수강정보를 수정하는 api입니다.<br>**반환 형식(리스트)**<br>과목ID<br>학수번호<br>과목명<br>과목영역<br>과목학점<br>점수<br>전공상태")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", description = "성공",
            content = @Content(schema = @Schema(implementation = TakeResponse.TakeListDTO.class))
    )
    @PostMapping("/update")
    public ApiResponse<TakeListDTO> updateTake(
            @LoginMember Member member,
            @RequestBody TakeRequest.TakeUpdateDTO takeUpdateDTO) {

        TakeListDTO takes = takeService.updateTakeByMember(member, takeUpdateDTO);

        return ApiResponse.onSuccess(takes);
    }

    @Operation(summary = "수강 정보 삭제 api", description = "현재 회원의 수강정보를 삭제하는 api입니다.<br>**반환 형식(리스트)**<br>과목ID<br>학수번호<br>과목명<br>과목영역<br>과목학점<br>점수<br>전공상태")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", description = "성공",
            content = @Content(schema = @Schema(implementation = TakeResponse.TakeListDTO.class))
    )
    @PostMapping("/delete")
    public ApiResponse<TakeListDTO> deleteTake(
            @LoginMember Member member,
            @RequestBody TakeRequest.TakeDeleteDTO takeDeleteDTO) {

        TakeListDTO takes = takeService.deleteTakeByMember(member, takeDeleteDTO);

        return ApiResponse.onSuccess(takes);
    }
}
