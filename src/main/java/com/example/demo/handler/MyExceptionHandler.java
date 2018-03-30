package com.example.demo.handler;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.exception.MyException;
import com.example.demo.util.JsonUtil;
import com.example.demo.vo.ResultVO;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class MyExceptionHandler {

	private boolean isJson(HttpServletRequest request) {
		String contentType = request.getContentType();
		return StringUtils.containsIgnoreCase(contentType, "json");
	}

	// 这个只能针对json数据返回
	// @ResponseBody
	// @ExceptionHandler(value = MyException.class)
	// public ResultVO errorHandler(MyException ex) {
	// return ResultVO.error(ex.getCode(), ex.getMessage());
	// }

	// 这个可以 同时处理 页面数据 以及 json数据
	@ExceptionHandler(value = MyException.class)
	public Object errorHandler(HttpServletRequest request, MyException ex, HttpServletResponse response) {
		log.info("ExceptionHandler........");
		
		if (isJson(request)) {
			response.setHeader("Content-Type", "application/json;charset=UTF-8");
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.write(JsonUtil.toJSONString(ResultVO.error(ex.getCode(), ex.getMessage())));
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error");
		return modelAndView;

	}
}
