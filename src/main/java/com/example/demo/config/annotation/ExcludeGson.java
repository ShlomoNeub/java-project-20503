package com.example.demo.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that indicates to the GSON strategy to exclude it from parsing
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcludeGson {

}
