package com.example.demo.config.annotation;

import com.example.demo.config.records.AuthInfo;
import jakarta.annotation.Nullable;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface AuthPayload {
    @Nullable
    Class<?>value() default AuthInfo.class;

}
