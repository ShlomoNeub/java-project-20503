package com.example.demo.config.interceptor;

import com.example.demo.config.annotation.ExcludeGson;
import com.example.demo.config.annotation.PrivateGson;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class GsonPrivateStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation(PrivateGson.class) != null ;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return clazz.getAnnotation(PrivateGson.class) != null ;
    }
}
