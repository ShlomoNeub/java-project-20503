/**
 * This file implement a custom constrain to jakarta
 */
package com.example.demo.db.entities.validator;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class EmailValidator implements ConstraintValidator<EmailValidator.Validate, String> {
    /**
     * Method for validation if given mail is from a valid format
     *
     * @param email string ti be tested
     * @return {@code true} if valid {@code false} otherwise
     */
    public static boolean isValidEmail(String email) {
        String regex = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        java.util.regex.Pattern pattern =
                java.util.regex.Pattern.compile(regex, java.util.regex.Pattern.CASE_INSENSITIVE);
        java.util.regex.Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * @param constraintAnnotation annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(Validate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * @param value   object to validate
     * @param context context in which the constraint is evaluated
     * @return if the constraint is valid
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isValidEmail(value);
    }

    /**
     * The validation annotation
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = EmailValidator.class)
    public @interface Validate {

        String message() default "Invalid email";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }

}
