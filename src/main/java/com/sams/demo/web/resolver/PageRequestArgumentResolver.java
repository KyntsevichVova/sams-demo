package com.sams.demo.web.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.sams.demo.common.ApplicationConstant.*;
import static org.apache.commons.lang3.StringUtils.isAnyBlank;
import static org.springframework.data.domain.PageRequest.of;

public class PageRequestArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(Pageable.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {

        int pageNum;
        int pageSize;

        String pageNumParam = nativeWebRequest.getParameter(PAGE_NUMBER_PARAMETER);
        String pageSizeParam = nativeWebRequest.getParameter(PAGE_SIZE_PARAMETER);

        if (isAnyBlank(pageNumParam, pageSizeParam)) {
            pageNum = DEFAULT_PAGE_NUMBER;
            pageSize = DEFAULT_PAGE_SIZE;
        } else {
            try {
                pageNum = Integer.parseInt(pageNumParam);
                pageSize = Integer.parseInt(pageSizeParam);
            } catch (NumberFormatException e) {
                pageNum = DEFAULT_PAGE_NUMBER;
                pageSize = DEFAULT_PAGE_SIZE;
            }
        }

        return of(pageNum, pageSize);
    }
}