package icurriculum.domain.member.dto;

import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.membermajor.MajorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public abstract class MemberResponse {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinResponse {
        String email;
        LocalDateTime join_date;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberInfo {
        String name;
        String email;
        Integer joinYear;
        List<MajorInfo> majorList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MajorInfo {
        MajorType majorType;
        DepartmentName departmentName;
    }
}
