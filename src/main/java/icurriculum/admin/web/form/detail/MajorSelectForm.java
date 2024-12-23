package icurriculum.admin.web.form.detail;

import icurriculum.global.valid.annotation.Code;
import icurriculum.admin.config.valid.annotation.ValidEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public record MajorSelectForm(

        Set<@Code String> codeSet,
        List<@ValidEntry Entry> additionalInfos
) {

    public MajorSelectForm {
        if (additionalInfos == null) {
            additionalInfos = new ArrayList<>();
        }
    }

}
