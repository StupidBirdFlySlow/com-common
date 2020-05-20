package com.cloud.server.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import com.cloud.server.common.bean.BaseResponseDto;
import com.cloud.server.common.exception.BusinessRuntimeException;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 */
@ControllerAdvice(annotations = RestController.class)
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Object exceptionHandler(HttpServletRequest request, Exception ex) {
		log.error("请求异常:", ex);
		BaseResponseDto responseDto = BaseResponseDto.error(ex.getMessage());
		// 业务异常处理
		if (ex instanceof BusinessRuntimeException) {
			responseDto.setMessage(ex.getMessage());
		} else {
			responseDto.setMessage("系统异常，请联系管理员");
		}
		responseDto.setErrorMsg(ex.getMessage());
		return responseDto;
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public Object handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		return BaseResponseDto.error("参数解析失败");
	}

	/**
	 * 405 - Method Not Allowed
	 */
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Object handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		log.error("不支持当前请求方法", e);
		return BaseResponseDto.error("不支持当前请求方法");
	}

	/**
	 * 415 - Unsupported Media Type
	 */
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public Object handleHttpMediaTypeNotSupportedException(Exception e) {
		log.error("不支持当前媒体类型", e);
		return BaseResponseDto.error("不支持当前媒体类型");
	}
}
