package com.sams.demo;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class OffsetPageHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(Pageable.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        String offsetParam = nativeWebRequest.getParameter("offset");
        String limitParam = nativeWebRequest.getParameter("limit");

        if (offsetParam == null) {
            offsetParam = "0";
        }
        if (limitParam == null) {
            limitParam = "20";
        }

        int offset;
        int limit;

        try {
            offset = Integer.parseInt(offsetParam);
            limit = Integer.parseInt(limitParam);
        } catch (NumberFormatException e) {
            offset = 0;
            limit = 20;
        }

        return new OffsetLimitPageRequest(offset, limit);
    }
}
