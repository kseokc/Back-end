package icurriculum.domain.member;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import icurriculum.domain.common.BaseRDBEntity;
import icurriculum.domain.membermajor.MemberMajor;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
@ToString
public class Member extends BaseRDBEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Email
    @Column(nullable = false)
    private String email;

    @Setter
    @Column(nullable = false)
    private String password;

    @Column(name = "member_name", nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer joinYear;

    @Enumerated(STRING)
    private RoleType role;

    @Builder
    private Member(String email, String name, Integer joinYear,RoleType roleType) {
        this.email = email;
        this.name = name;
        this.joinYear = joinYear;
        this.role = roleType;
    }

}
