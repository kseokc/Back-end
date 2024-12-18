package icurriculum.global.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginDTO {
    private String email;
    private Long memberId;

}
