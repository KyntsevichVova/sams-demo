package com.sams.demo.web.resolver;

import com.sams.demo.model.enums.LevelType;
import com.sams.demo.web.annotation.Level;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.sams.demo.common.ApplicationConstant.LEVEL_PARAMETER;
import static com.sams.demo.model.error.ErrorHandler.EXCEPTION_DETAILS_PATTERN;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
public class LevelArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {

        return methodParameter.hasParameterAnnotation(Level.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {

        LevelType level;

        String levelParam = nativeWebRequest.getParameter(LEVEL_PARAMETER);

        if (isBlank(levelParam)) {
            return null;
        } else {
            try {
                level = LevelType.valueOf(levelParam.toUpperCase());
            } catch (Exception ex) {

                log.error(format(EXCEPTION_DETAILS_PATTERN, ex));
                return null;
            }
        }

        return level.name();
    }
}