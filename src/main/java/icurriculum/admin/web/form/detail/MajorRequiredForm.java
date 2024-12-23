package icurriculum.admin.web.form.detail;

import icurriculum.global.valid.annotation.Code;
import icurriculum.admin.config.valid.annotation.ValidEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public record MajorRequiredForm(

        Set<@Code String> codeSet,
        List<@ValidEntry Entry> additionalInfos
) {

    public MajorRequiredForm {
        if (additionalInfos == null) {
            additionalInfos = new ArrayList<>();
        }
    }

}
