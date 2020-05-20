package com.cloud.server.common.exception;

import org.springframework.core.NestedRuntimeException;

public class BusinessRuntimeException extends NestedRuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusinessRuntimeException(String msg) {
		super(msg);
	}

	public BusinessRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}
}