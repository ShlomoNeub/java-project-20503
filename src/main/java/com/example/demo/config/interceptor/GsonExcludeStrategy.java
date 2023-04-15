package com.example.demo.config.interceptor;

import com.example.demo.config.annotation.ExcludeGson;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class GsonExcludeStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes f) {

        return f.getAnnotation(ExcludeGson.class) != null ;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return clazz.getAnnotation(ExcludeGson.class) != null ;
    }
}
