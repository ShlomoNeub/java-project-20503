package com.example.demo.config.interceptor;

import com.example.demo.config.interfaces.AuthPayload;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthPayloadArgumentResolver implements HandlerMethodArgumentResolver {
    final static Logger logger = LogManager.getLogger(AuthPayloadArgumentResolver.class);
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(AuthPayload.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        AuthPayload authPayloadAnnotation = parameter.getParameterAnnotation(AuthPayload.class);
        if (authPayloadAnnotation != null) {
            HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
                // if the annotation specifies that the payload is a JWT token,
                // you can decode the token and populate the parameter with its contents
                String[] token = httpServletRequest.getHeader("Auth").split(":");
                try{

                return token[authPayloadAnnotation.jwt()?1:0];
                }catch (Exception e){
                    logger.error(e);
                    return null;
                }
        }
        return null;
    }
}
