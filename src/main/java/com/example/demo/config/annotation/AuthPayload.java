/**
 * Annotation that indicates the parameter should be filled with the Auth info,
 * <br/>
 * by {@code AuthPayloadArgumentResolver}
 */
package com.example.demo.config.annotation;

import com.example.demo.config.records.AuthInfo;
import jakarta.annotation.Nullable;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface AuthPayload {
    @Nullable
    Class<?> value() default AuthInfo.class;

}
