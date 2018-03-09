package com.example.demo.exception;

import com.example.demo.enums.MyExceptionEnum;

import lombok.Getter;

@Getter
public class MyException extends RuntimeException {
	
	private Integer code;
	
	public MyException(Integer code, String msg) {
		super(msg);
		this.code = code;
	}
	
	public MyException(MyExceptionEnum exceptionEnum) {
		super(exceptionEnum.getMsg());
		this.code = exceptionEnum.getCode();
	}
	
}
