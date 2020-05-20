package com.cloud.server.common.config;

import java.io.IOException;
import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.cloud.server.common.bean.BaseResponseDto;

/**
 * 对controller返回的数据统一封装为ResponseInfo，注意： 1、controller异常由spring mvc异常机制处理，会跳过该处理器
 * 2、该处理器仅处理包含@RestController、@ResponseBody注解的控制器
 */
public class MethodReturnValueHandler extends RequestResponseBodyMethodProcessor {

	public MethodReturnValueHandler(List<HttpMessageConverter<?>> converters) {
		super(converters);
	}

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {

		Class<?> controllerClass = returnType.getContainingClass();
		returnType.getMethodAnnotation(ResponseBody.class);

		return controllerClass.isAnnotationPresent(RestController.class)
				|| controllerClass.isAnnotationPresent(ResponseBody.class)
				|| returnType.getMethodAnnotation(ResponseBody.class) != null;
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws IOException, HttpMediaTypeNotAcceptableException {
		if (((ServletWebRequest) webRequest).getResponse().isCommitted()) {
			super.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
			return;
		}
		BaseResponseDto responseInfo = null;
		if (returnValue instanceof BaseResponseDto) {
			responseInfo = (BaseResponseDto) returnValue;
		} else {
			if (webRequest instanceof ServletWebRequest) {
				ServletWebRequest request = (ServletWebRequest) webRequest;
				String path = request.getRequest().getServletPath();
				// 对外接口，特殊处理
				if (returnValue != null) {
					if (returnValue.getClass().toString().contains("DCBaseResponseDto")
							&& (path.endsWith("uploadFileToPath") || path.endsWith("uploadFiles"))) {
						super.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
						return;
					}
				}
				if (request.getRequest().getServletPath().startsWith("/actuator")) {
					super.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
					return;
				}
			}
			responseInfo = new BaseResponseDto();
			responseInfo.setData(returnValue);
		}

		super.handleReturnValue(responseInfo, returnType, mavContainer, webRequest);

	}
}