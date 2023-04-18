package com.example.demo.config.annotation;
import java.lang.annotation.*;


/**
 * Annotation that tells to system middleware
 * that it should be authenticated
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface Auth {
    /**
     * @return the minimum role level that can access it;
     */
    int minLevel() default 0;

}
