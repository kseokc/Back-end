package icurriculum.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

public abstract class MemberRequest {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinDTO{
        @NotNull
        @Email
        private String email;

        @NotNull
        private String password;

        @NotNull
        private String name;

        @NotNull
        private Long departmentId;

        @NotNull
        private Integer joinYear;
    }
}
