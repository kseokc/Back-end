package icurriculum.admin.web.form.detail;

import icurriculum.global.valid.annotation.Code;
import icurriculum.admin.config.valid.annotation.ValidEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public record GeneralRequiredForm(

        Set<@Code String> codeSet,
        List<@ValidEntry Entry> additionalInfos
) {

    public GeneralRequiredForm {
        if (additionalInfos == null) {
            additionalInfos = new ArrayList<>();
        }
    }

}
