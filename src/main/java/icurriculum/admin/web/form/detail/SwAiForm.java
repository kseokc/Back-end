package icurriculum.admin.web.form.detail;

import icurriculum.global.valid.annotation.Code;
import icurriculum.admin.config.valid.annotation.ValidEntry;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.hibernate.validator.constraints.Range;

public record SwAiForm(
        Set<@Code String> approvedCodeSet,
        Set<@Code String> areaAlternativeCodeSet,

        @NotNull
        @Range(min = 0, max = 10)
        Integer requiredCredit,

        List<@ValidEntry Entry> additionalInfos
) {

    public SwAiForm {
        if (additionalInfos == null) {
            additionalInfos = new ArrayList<>();
        }
    }

}
