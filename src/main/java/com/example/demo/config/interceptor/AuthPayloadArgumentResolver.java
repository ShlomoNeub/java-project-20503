/**
 * This file contains the implantation of the AuthInfo resolver.
 * Based on HandlerMethodArgumentResolver
 *
 */
package com.example.demo.config.interceptor;

import com.example.demo.config.annotation.AuthPayload;
import com.example.demo.config.records.AuthInfo;
import com.example.demo.db.entities.JsonWebToken;
import com.example.demo.db.entities.Users;
import com.example.demo.db.repo.JwtRepo;
import com.example.demo.db.repo.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Component
public class AuthPayloadArgumentResolver implements HandlerMethodArgumentResolver {

    final UserRepo userRepo;

    final JwtRepo jwtRepo;

    final static Logger logger = LogManager.getLogger(AuthPayloadArgumentResolver.class);

    public AuthPayloadArgumentResolver(UserRepo userRepo, JwtRepo jwtRepo) {
        this.userRepo = userRepo;
        this.jwtRepo = jwtRepo;
    }

    /**
     * @param parameter the method parameter to check
     * @return {@code true} if this resolver supports the supplied parameter;
     * {@code false} otherwise
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(AuthPayload.class) != null &&
                parameter.getParameterType() == AuthInfo.class;
    }


    /**
     * @param parameter     the method parameter to resolve. This parameter must
     *                      have previously been passed to {@link #supportsParameter} which must
     *                      have returned {@code true}.
     * @param mavContainer  the ModelAndViewContainer for the current request
     * @param webRequest    the current request
     * @param binderFactory a factory for creating {@link WebDataBinder} instances
     * @return The populated {@code AuthInfo} otherwise {@code null}
     */
    @Override
    public Object resolveArgument(@NonNull MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  @NonNull NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        if(!supportsParameter(parameter))
            throw new IllegalArgumentException("Parameter must be of type AuthInfo");
        try {
            HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
            if (httpServletRequest == null || httpServletRequest.getHeader("Auth") == null) {
                logger.warn("Invalid request");
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String[] token = httpServletRequest.getHeader("Auth").split(":");
            if (token.length != 2) {
                return null;
            }
            String uid = token[0], uuid = token[1];
            Users user = userRepo.findById(Integer.parseInt(uid)).orElse(null);
            JsonWebToken jwt = jwtRepo.findByUUID(UUID.fromString(uuid)).orElse(null);

            if (user == null || jwt == null) {
                logger.warn("No valid credentials");
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }

            try {
                return new AuthInfo(user, jwt);
            } catch (Exception e) {
                logger.error(e);
                return null;
            }
        } catch (Exception e) {
            if (e instanceof ResponseStatusException) throw e;
            else {
                logger.error(e);
                return null;
            }
        }
    }
}
