package com.example.repairagency.annotation;

import com.example.repairagency.config.LocalizationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameValidator implements ConstraintValidator<ValidName, String> {
    private final MessageSource messageSource;

    @Autowired
    public NameValidator(MessageSource messageSource){
        this.messageSource = messageSource;
    }
        private Pattern pattern;
        private Matcher matcher;


        @Override
        public void initialize(ValidName constraintAnnotation) {
        }

        @Override
        public boolean isValid(String name, ConstraintValidatorContext context) {
            return (validateName(name));
        }

        private boolean validateName(String name) {
            pattern = Pattern.compile(messageSource.getMessage("name.regex", null, LocaleContextHolder.getLocale()));
            matcher = pattern.matcher(name);
            return matcher.matches();
        }
}
