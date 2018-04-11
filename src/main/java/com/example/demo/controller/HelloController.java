package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.enums.MyExceptionEnum;
import com.example.demo.exception.MyException;
import com.example.demo.vo.ResultVO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HelloController {

	@GetMapping("/hello")
	@ResponseBody
	@ApiOperation(value="hello api...... 返回  hello")
	public ResultVO<String> hello (@ApiParam(value= "姓名") String name){
		ResultVO result = new ResultVO<>();
		result.setCode(0);
		result.setMsg("ok");
		result.setData("Hello, " + name);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (name.equalsIgnoreCase("qqq") ) {
			throw new MyException(MyExceptionEnum.AUTH_EXCEPTION);
		}
		
		return result;
	}
	
	@RequestMapping("/hello_page")
	public ModelAndView helloPage(String name ) {
		
		if (name.equalsIgnoreCase("qqq") ) {
			throw new MyException(MyExceptionEnum.AUTH_EXCEPTION);
		}
		
		ModelAndView result = new ModelAndView();
		result.setViewName("hello");
		return result;
	}
	
	@RequestMapping("/hello_page2")
	public String helloPage2(String name ) {
		
		if (name.equalsIgnoreCase("qqq") ) {
			throw new MyException(MyExceptionEnum.AUTH_EXCEPTION);
		}
		
		return "hello";
	}
	
}
