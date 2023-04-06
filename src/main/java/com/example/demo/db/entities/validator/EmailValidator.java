package com.example.demo.db.entities.validator;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

public class EmailValidator implements ConstraintValidator<EmailValidator.Validate, String> {
    @Override
    public void initialize(Validate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        java.util.regex.Pattern pattern =
                java.util.regex.Pattern.compile(regex, java.util.regex.Pattern.CASE_INSENSITIVE);
        java.util.regex.Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isValidEmail(value);
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = EmailValidator.class)
    public @interface Validate {

        String message() default "Invalid email";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }

}
