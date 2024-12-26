package icurriculum.admin.web.form.detail;

import icurriculum.global.valid.annotation.Code;
import icurriculum.admin.config.valid.annotation.ValidEntry;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.hibernate.validator.constraints.Range;

public record CreativityForm(
        Set<@Code String> approvedCodeSet,

        @NotNull
        @Range(min = 0, max = 10)
        Integer requiredCredit,

        List<@ValidEntry Entry> additionalInfos
) {

    public CreativityForm {
        if (additionalInfos == null) {
            additionalInfos = new ArrayList<>();
        }
    }

}
