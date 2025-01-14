package icurriculum.domain.categoryjudge;

import static icurriculum.domain.take.Category.교양선택;
import static icurriculum.domain.take.Category.핵심교양1;
import static icurriculum.domain.take.Category.핵심교양2;
import static icurriculum.domain.take.Category.핵심교양3;
import static icurriculum.domain.take.Category.핵심교양4;
import static icurriculum.domain.take.Category.핵심교양5;
import static icurriculum.domain.take.Category.핵심교양6;

import icurriculum.domain.curriculum.Curriculum;
import icurriculum.domain.curriculum.data.AlternativeCourse;
import icurriculum.domain.curriculum.data.Core;
import icurriculum.domain.take.Category;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CategoryJudgeUtils {

    // 성적표용 판별 실행 함수
    public static Map<String, Category> judge(List<String> codes, Curriculum curriculum) {
        return runJudge(codes, curriculum);
    }

    // 단건 조회용 판별 실행 함수
    public static Map<String, Category> judge(String code, Curriculum curriculum) {
        List<String> codes = new ArrayList<>();
        codes.add(code);
        return runJudge(codes, curriculum);
    }

    // 복수 조회용 판별 실행  함수
    public static Map<String, Category> judges(List<String> codes, Curriculum curriculum){
        return runJudge(codes, curriculum);
    }

    private static Map<String, Category> runJudge(List<String> codes,
        Curriculum curriculum) {
        Map<String, Category> judgeCodes = new HashMap<>();

        Set<String> majorRequired = curriculum.getMajorRequired().getCodeSet();
        Set<String> majorSelect = curriculum.getMajorSelect().getCodeSet();
        Set<String> generalRequired = curriculum.getGeneralRequired().getCodeSet();
        Set<String> creativityCodes = curriculum.getCreativity().getApprovedCodeSet();

        Set<String> swAiCodes = curriculum.getSwAi().getApprovedCodeSet();
        Set<String> swAialternativeCodes = curriculum.getSwAi().getAreaAlternativeCodeSet();

        AlternativeCourse alternativeCourse = curriculum.getAlternativeCourse();

        for (String code : codes) {
            Category category = judgeCore(code, curriculum);  // 핵심교양 판단
            if (isCoreCode(category)) {
                judgeCodes.put(code, category);
                continue;
            }  // 핵심교양이 아닌 과목들의 다른영역 확인
            if (!creativityCodes.isEmpty() && creativityCodes.contains(code)) {
                judgeCodes.put(code, Category.창의);
                continue;
            }
            if (((!swAiCodes.isEmpty() && swAiCodes.contains(code)) || (
                swAialternativeCodes != null && swAialternativeCodes.contains(code)))) {
                judgeCodes.put(code, Category.SW_AI);
                continue;
            }
            if (majorRequired.contains(code)) {
                judgeCodes.put(code, Category.전공필수);
                continue;
            }
            if (majorSelect.contains(code)) {
                judgeCodes.put(code, Category.전공선택);
                continue;
            }
            if (generalRequired.contains(code)) {
                judgeCodes.put(code, Category.교양필수);
                continue;
            }

            Set<String> alternativeCodeSet = alternativeCourse.getAlternativeCodeSet(code);
            if (!alternativeCodeSet.isEmpty()) {
                judgeCodes.put(code, judgeAlternative(alternativeCodeSet, curriculum));
                continue;
            }
            judgeCodes.put(code, 교양선택);

        }
        return judgeCodes;
    }

    /**
     * 사용자 핵심교양 판별 메서드
     *
     * @return 핵심교양 영역
     **/
    public static Category judgeCore(String code, Curriculum curriculum) {

        Core core = curriculum.getCore();
        Boolean isAreaFixed = core.getIsAreaFixed();

        Category category = 교양선택;

        if (code.startsWith("GEC") || code.startsWith("GED")) {
            char coreArea = code.charAt(3); // 핵교 영역
            switch (coreArea) {
                case '1' -> category = Category.핵심교양1;
                case '2' -> category = 핵심교양2;
                case '3' -> category = Category.핵심교양3;
                case '4' -> category = Category.핵심교양4;
                case '5' -> category = Category.핵심교양5;
                case '6' -> category = Category.핵심교양6;
                default -> category = 교양선택;
            }
        }
        if (!isAreaFixed) {
            return notAreaFixed(category, code, core);
        }
        return areaFixed(category, code, core);
    }

    public static Boolean isCoreCode(Category category) {
        return !category.equals(교양선택);
    }


    public static Category judgeAlternative(Set<String> codes, Curriculum curriculum) {
        /**
         * @params codes : 해당과목의 대체가능한 과목들
         */

        Set<String> majorRequired = curriculum.getMajorRequired().getCodeSet();
        Set<String> majorSelect = curriculum.getMajorSelect().getCodeSet();
        Set<String> generalRequired = curriculum.getGeneralRequired().getCodeSet();

        Set<String> creativityCodes = curriculum.getCreativity().getApprovedCodeSet();

        Set<String> swAiCodes = curriculum.getSwAi().getApprovedCodeSet();
        Set<String> swAialternativeCodes = curriculum.getSwAi().getAreaAlternativeCodeSet();

        for (String code : codes) {
            Category category = judgeCore(code, curriculum);  // 핵심교양 판단
            if (isCoreCode(category)) {
                return category;
            }
            // 핵심교양이 아닌 과목들의 다른영역 확인
            if (creativityCodes != null && creativityCodes.contains(code)) {
                return Category.창의;
            }
            if (((swAiCodes != null && swAiCodes.contains(code)) || (
                swAialternativeCodes != null && swAialternativeCodes.contains(code)))) {
                return Category.SW_AI;
            }
            if (majorRequired.contains(code)) {
                return Category.전공필수;
            }
            if (majorSelect.contains(code)) {
                return Category.전공선택;
            }
            if (generalRequired.contains(code)) {
                return Category.교양필수;
            }
        }
        return 교양선택;
    }

    public static Category notAreaFixed(Category category, String code, Core core) {
        if (category != 교양선택) {
            return category;
        }
        return getDeterminedCategory(code, core);
    }

    public static Category areaFixed(Category category, String code, Core core) {
        if (category != 교양선택) {
            Set<Category> requiredAreaSet = core.getRequiredAreaSet();
            if (!requiredAreaSet.contains(category)) {
                return 교양선택;
            } else {
                if (core.getAreaDeterminedCodeSet(category).isEmpty()) {
                    return category;
                }
            }
        }
        return getDeterminedCategory(code, core);
    }

    public static Category getDeterminedCategory(String code, Core core) {
        Set<String> determinedArea1 = core.getAreaDeterminedCodeSet(핵심교양1);
        Set<String> determinedArea2 = core.getAreaDeterminedCodeSet(핵심교양2);
        Set<String> determinedArea3 = core.getAreaDeterminedCodeSet(핵심교양3);
        Set<String> determinedArea4 = core.getAreaDeterminedCodeSet(핵심교양4);
        Set<String> determinedArea5 = core.getAreaDeterminedCodeSet(핵심교양5);
        Set<String> determinedArea6 = core.getAreaDeterminedCodeSet(핵심교양6);
        if (determinedArea1.contains(code)) {
            return 핵심교양1;
        }
        if (determinedArea2.contains(code)) {
            return 핵심교양2;
        }
        if (determinedArea3.contains(code)) {
            return 핵심교양3;
        }
        if (determinedArea4.contains(code)) {
            return 핵심교양4;
        }
        if (determinedArea5.contains(code)) {
            return 핵심교양5;
        }
        if (determinedArea6.contains(code)) {
            return 핵심교양6;
        }
        return 교양선택;
    }
}
