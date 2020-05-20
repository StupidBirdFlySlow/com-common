package com.cloud.server.common.config;

import com.alibaba.fastjson.JSON;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class ArgumentsResolver implements HandlerMethodArgumentResolver {
    private static final String JSON_REQUEST_BODY = "JSON_REQUEST_BODY";

    /**
     * 解析器是否支持当前参数
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JsonParam.class);
    }

    //当支持后进行相应的转换
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String body = webRequest.getParameter(parameter.getParameterName());

        Object  val = JSON.parseObject(body, parameter.getGenericParameterType());
            if (parameter.getParameterAnnotation(JsonParam.class).required() && val == null) {
                throw new IllegalStateException(parameter.getParameterAnnotation(JsonParam.class).value() + "不能为空");
            }
        return val;
    }

}