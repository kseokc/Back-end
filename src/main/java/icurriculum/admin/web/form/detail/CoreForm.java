package icurriculum.admin.web.form.detail;

import icurriculum.global.valid.annotation.Code;
import icurriculum.admin.config.valid.annotation.ValidCategory;
import icurriculum.admin.config.valid.annotation.ValidEntry;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.hibernate.validator.constraints.Range;

public record CoreForm(

        @NotNull
        Boolean isAreaFixed,

        @NotNull
        @Range(min = 0, max = 20)
        Integer requiredCredit,

        Set<@ValidCategory String> requiredAreaSet,

        Set<@Code String> areaDeterminedCodeSetOne,
        Set<@Code String> areaDeterminedCodeSetTwo,
        Set<@Code String> areaDeterminedCodeSetThree,
        Set<@Code String> areaDeterminedCodeSetFour,
        Set<@Code String> areaDeterminedCodeSetFive,
        Set<@Code String> areaDeterminedCodeSetSix,

        Set<@Code String> areaAlternativeCodeSetOne,
        Set<@Code String> areaAlternativeCodeSetTwo,
        Set<@Code String> areaAlternativeCodeSetThree,
        Set<@Code String> areaAlternativeCodeSetFour,
        Set<@Code String> areaAlternativeCodeSetFive,
        Set<@Code String> areaAlternativeCodeSetSix,

        List<@ValidEntry Entry> additionalInfos
) {

    public CoreForm {
        if (isAreaFixed == null) {
            isAreaFixed = false;
        }

        if (additionalInfos == null) {
            additionalInfos = new ArrayList<>();
        }
    }

}
