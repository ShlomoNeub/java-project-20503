package com.example.demo.db.entities.validator;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class PhoneValidator implements ConstraintValidator<PhoneValidator.Validate, String> {

    @Override
    public void initialize(Validate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    public static boolean isValidPhone(String email) {
        String regex = "^[+]?[(]?[0-9]{3}[)]?[-\\s.]?[0-9]{3}[-\\s.]?[0-9]{4,6}$";
        java.util.regex.Pattern pattern =
                java.util.regex.Pattern.compile(regex, java.util.regex.Pattern.CASE_INSENSITIVE);
        java.util.regex.Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isValidPhone(value);
    }



    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = PhoneValidator.class)
    public @interface Validate {

        String message() default "Invalid phone";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }

}
