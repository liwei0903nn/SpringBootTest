package com.example.demo.enums;

import lombok.Getter;

@Getter
public enum MyExceptionEnum {
	
	
	AUTH_EXCEPTION(1, "认证失败"),
	UNKOWN_ERROR(2, "位置错误"),
	;
	

	private Integer code;
	private String  msg;
	
	MyExceptionEnum (Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

}
