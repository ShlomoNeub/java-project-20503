/**
 * Here we implement a layer in the calls flow were handle all custom annotations
 */
package com.example.demo.config.interceptor;

import com.example.demo.config.annotation.Auth;
import com.example.demo.db.entities.JsonWebToken;
import com.example.demo.db.repo.JwtRepo;
import com.example.demo.db.repo.UserRepo;
import jakarta.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Basic middleware
 */
@RestControllerAdvice
public class MiddlewareSystem {
    final Logger logger = LogManager.getLogger(MiddlewareSystem.class);
    final
    JwtRepo jwtRepo;

    final
    UserRepo userRepo;

    public MiddlewareSystem(JwtRepo jwtRepo, UserRepo userRepo) {
        this.jwtRepo = jwtRepo;
        this.userRepo = userRepo;
    }

    /**
     * Hash of all the annotation and the methode used for the annotation
     */
    HashMap<Class<? extends Annotation>, Method> validators = new HashMap<>() {{
        try {
            put(Auth.class, MiddlewareSystem.class.getMethod("doAuth", WebRequest.class));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }};

    /**
     * This methode validates that the request Auth is valid in the database
     *
     * @param request that is being made to the server
     * @throws ResponseStatusException on Authorization error
     */
    public void doAuth(WebRequest request) throws ResponseStatusException {
        HandlerMethod handlerMethod = (HandlerMethod) request.getAttribute(
                HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE, WebRequest.SCOPE_REQUEST);

        Method selfMethod = Objects.requireNonNull(handlerMethod).getMethod();
        Auth auth = selfMethod.getAnnotation(Auth.class);
        String controllerName = handlerMethod.getBeanType().getSimpleName();
        String methodName = selfMethod.getName();
        String authHeader = request.getHeader("Auth");
        String[] authSplit = authHeader != null ? authHeader.split(":") : new String[0];


        logger.debug("Executing @Auth on " + methodName + " in " + controllerName);

        if (authSplit.length != 2) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        if (!authUser(Integer.parseInt(authSplit[0]), authSplit[1], auth.minLevel())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

    }

    /**
     * @param request that is being made to the server
     * @throws ResponseStatusException on any error
     */
    @ModelAttribute
    public void handler(WebRequest request) throws ResponseStatusException {
        // gets the methode that will be executed
        boolean error = false;
        HandlerMethod handlerMethod = (HandlerMethod) request.getAttribute(
                HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE, WebRequest.SCOPE_REQUEST);

        if (handlerMethod != null) {
            // iterate on all the validator and check if the need to be run;
            for (Map.Entry<Class<? extends Annotation>, Method> entry : validators.entrySet()) {
                Class<? extends Annotation> testAnnotation = entry.getKey();
                Method method = entry.getValue();
                // check if the target is annotated with testAnnotation
                if (handlerMethod.getMethodAnnotation(testAnnotation) != null) {
                    try {
                        // invoke the validation function
                        method.invoke(this, request);
                    } catch (InvocationTargetException e) {
                        if (e.getTargetException() instanceof ResponseStatusException)
                            throw (ResponseStatusException) e.getTargetException();
                        else {
                            logger.error(e);
                            error = true;
                        }
                    } catch (IllegalAccessException e) {
                        logger.error(e);
                        error = true;
                    }

                }
            }
            if (error) throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Authenticated the user with his id and jwt
     *
     * @param userID of the target user
     * @param jwt    of the user to access
     * @param level  the minimal level that user must have
     * @return if the user is authenticated
     */
    public boolean authUser(Integer userID, String jwt, @Nullable Integer level) {
        Optional<JsonWebToken> tokenOptional = jwtRepo.getTokenByUser(userID);

        return tokenOptional.isPresent() && // token exsists
                tokenOptional.get().getValid() && // not expired
                tokenOptional.get().getJwt().toString().equals(jwt) && // same as given jwt
                tokenOptional.get().getUser().getRole().getRoleLevel() >= level; // have higher or equal access
    }


}
