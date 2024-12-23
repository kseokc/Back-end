package icurriculum.global.valid.validator;

import icurriculum.global.valid.annotation.Code;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class CodeValidator implements ConstraintValidator<Code, String> {

    private static final Pattern CODE_PATTERN = Pattern.compile("^[A-Z]{3}\\d{4}$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank() || value.isEmpty()) {
            return false;
        }
        return CODE_PATTERN.matcher(value).matches();
    }
}
