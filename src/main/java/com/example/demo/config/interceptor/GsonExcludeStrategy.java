package com.example.demo.config.interceptor;

import com.example.demo.config.annotation.ExcludeGson;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import java.lang.annotation.Annotation;

public class GsonExcludeStrategy implements ExclusionStrategy {

    private final Class<? extends Annotation> clazz;

    public GsonExcludeStrategy() {
        this.clazz = ExcludeGson.class;
    }

    public GsonExcludeStrategy(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean shouldSkipField(FieldAttributes f) {

        return f.getAnnotation(clazz) != null ||f.getAnnotation(JsonBackReference.class) != null  ;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return clazz.getAnnotation(ExcludeGson.class) != null ;
    }
}
