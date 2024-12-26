package icurriculum.admin.service;

import icurriculum.admin.config.exception.AdminGeneralException;
import icurriculum.admin.web.converter.CurriculumConverter;
import icurriculum.admin.web.form.ModifyForm;
import icurriculum.admin.web.form.ModifyParam;
import icurriculum.admin.web.form.detail.AlternativeCourseForm;
import icurriculum.admin.web.form.detail.CoreForm;
import icurriculum.admin.web.form.detail.CreativityForm;
import icurriculum.admin.web.form.detail.GeneralRequiredForm;
import icurriculum.admin.web.form.detail.MajorRequiredForm;
import icurriculum.admin.web.form.detail.MajorSelectForm;
import icurriculum.admin.web.form.detail.RequiredCreditForm;
import icurriculum.admin.web.form.detail.SwAiForm;
import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.CurriculumDecider;
import icurriculum.domain.curriculum.data.AlternativeCourse;
import icurriculum.domain.curriculum.data.Core;
import icurriculum.domain.curriculum.data.Creativity;
import icurriculum.domain.curriculum.data.GeneralRequired;
import icurriculum.domain.curriculum.data.MajorRequired;
import icurriculum.domain.curriculum.data.MajorSelect;
import icurriculum.domain.curriculum.data.RequiredCredit;
import icurriculum.domain.curriculum.data.SwAi;
import icurriculum.domain.curriculum.repository.CurriculumRepository;
import icurriculum.domain.department.DepartmentName;
import icurriculum.domain.membermajor.MajorType;
import icurriculum.global.response.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final CurriculumRepository repository;

    public boolean isNotExist(ModifyForm form) {
        CurriculumDecider decider = createDecider(
                form.getMajorType(),
                form.getDepartmentName(),
                form.getJoinYear()
        );

        return repository.findByDecider(decider).isEmpty();
    }

    public boolean isNotExist(ModifyParam param) {
        CurriculumDecider decider = createDecider(
                param.getMajorType(),
                param.getDepartmentName(),
                param.getJoinYear()
        );

        return repository.findByDecider(decider).isEmpty();
    }

    public Curriculum getCurriculum(ModifyParam param) {
        CurriculumDecider decider = createDecider(
                param.getMajorType(), param.getDepartmentName(),
                param.getJoinYear()
        );

        return repository.findByDecider(decider).orElseThrow(
                () -> new AdminGeneralException(ErrorStatus.CURRICULUM_NOT_FOUND)
        );
    }

    public Curriculum modifyRequiredCredit(
            ModifyParam param,
            RequiredCreditForm requiredCreditForm
    ) {
        Curriculum curriculum = this.getCurriculum(param);
        RequiredCredit requiredCredit = CurriculumConverter.to(requiredCreditForm);
        curriculum.updateRequiredCredit(requiredCredit);

        return repository.save(curriculum);
    }

    public Curriculum modifyMajorRequired(
            ModifyParam param,
            MajorRequiredForm majorRequiredForm
    ) {
        Curriculum curriculum = this.getCurriculum(param);
        MajorRequired majorRequired = CurriculumConverter.to(majorRequiredForm);
        curriculum.updateMajorRequired(majorRequired);

        return repository.save(curriculum);
    }

    public Curriculum modifySwAi(ModifyParam param, SwAiForm swAiForm) {
        Curriculum curriculum = this.getCurriculum(param);
        SwAi swAi = CurriculumConverter.to(swAiForm);
        curriculum.updateSwAi(swAi);

        return repository.save(curriculum);
    }

    public Curriculum modifyCore(ModifyParam param, CoreForm coreForm) {
        Curriculum curriculum = this.getCurriculum(param);
        Core core = CurriculumConverter.to(coreForm);
        curriculum.updateCore(core);

        return repository.save(curriculum);
    }

    public Curriculum modifyCreativity(ModifyParam param, CreativityForm creativityForm) {
        Curriculum curriculum = this.getCurriculum(param);
        Creativity creativity = CurriculumConverter.to(creativityForm);
        curriculum.updateCreativity(creativity);

        return repository.save(curriculum);
    }

    public Curriculum modifyGeneralRequired(
            ModifyParam param,
            GeneralRequiredForm generalRequiredForm
    ) {
        Curriculum curriculum = this.getCurriculum(param);
        GeneralRequired generalRequired = CurriculumConverter.to(generalRequiredForm);
        curriculum.updateGeneralRequired(generalRequired);

        return repository.save(curriculum);
    }

    public Curriculum modifyMajorSelect(
            ModifyParam param,
            MajorSelectForm majorSelectForm
    ) {
        Curriculum curriculum = this.getCurriculum(param);
        MajorSelect majorSelect = CurriculumConverter.to(majorSelectForm);
        curriculum.updateMajorSelect(majorSelect);

        return repository.save(curriculum);
    }

    public Curriculum modifyAlternativeCourse(
            ModifyParam param,
            AlternativeCourseForm form
    ) {
        Curriculum curriculum = this.getCurriculum(param);
        AlternativeCourse alternativeCourse = CurriculumConverter.to(form);
        curriculum.updateAlternativeCourse(alternativeCourse);

        return repository.save(curriculum);
    }

    private CurriculumDecider createDecider(
            String majorType, String departmentName, Integer joinYear) {
        return CurriculumDecider.builder()
                .majorType(MajorType.to(majorType))
                .departmentName(DepartmentName.to(departmentName))
                .joinYear(joinYear)
                .build();
    }

}
