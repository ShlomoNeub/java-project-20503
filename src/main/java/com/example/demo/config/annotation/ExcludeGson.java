package com.example.demo.config.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation that indicates to the GSON strategy to exclude it from parsing
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcludeGson {

}
