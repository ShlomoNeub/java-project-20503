/**
 * This class defines an exclusion strategy for Gson Serializer
 */
package com.example.demo.config.interceptor;

import com.example.demo.config.annotation.ExcludeGson;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import java.lang.annotation.Annotation;

/**
 * Strategy to exclude all parameter annotated with clazz
 */
public class GsonExcludeStrategy implements ExclusionStrategy {

    private final Class<? extends Annotation> clazz;

    /**
     * Excludes all with fields {@code @ExcludeGson}
     */
    public GsonExcludeStrategy() {
        this.clazz = ExcludeGson.class;
    }

    /**
     * Excludes all with fields annotated {@code clazz}
     * @param clazz annotation the should be excluded
     */
    public GsonExcludeStrategy(Class<? extends Annotation> clazz) {
        this.clazz = clazz;
    }

    /**
     * @param f the field object that is under test
     * @return if a field should be skipped base on {@code clazz} annotation
     */
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation(clazz) != null || f.getAnnotation(JsonBackReference.class) != null;
    }

    /**
     *
     * @param clazz the class object that is under test
     * @return if a field class should be skipped base on {@code clazz} annotation
     */
    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return clazz.getAnnotation(ExcludeGson.class) != null;
    }
}
