package com.sams.demo.web.resolver;

import com.sams.demo.model.entity.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.sams.demo.common.ApplicationConstant.*;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class LevelArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {

        return methodParameter.getParameterType().equals(String.class)
                && methodParameter.getParameterName() != null
                && methodParameter.getParameterName().equals(LEVEL_PARAMETER);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {

        Question.Level level;

        String levelParam = nativeWebRequest.getParameter(LEVEL_PARAMETER);

        if (isBlank(levelParam)) {
            return null;
        } else {
            try {
                level = Question.Level.valueOf(levelParam.toUpperCase());
            } catch (Exception e) {
                return null;
            }
        }

        return level.name();
    }
}
