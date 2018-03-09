package com.example.demo.aspect;

import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.exception.MyException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class ControllerAspect {
	
	@Pointcut("execution(* com.example.demo.controller2.*.*(..))")
	public void controllerLog() {
		
	}
	
	@Before("controllerLog()")
	public void before(JoinPoint joinPoint) {
		
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();


        // 记录下请求内容
        log.info("URL : " + request.getRequestURL().toString());
        log.info("HTTP_METHOD : " + request.getMethod());
        log.info("IP : " + request.getRemoteAddr());
        log.info("HEADERS : " + request.getHeaderNames());
        log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature
                ().getName());
        log.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));

        //获取所有参数方法一：

        Enumeration<String> enu = request.getParameterNames();

        while (enu.hasMoreElements()) {

            String paraName = enu.nextElement();

            log.info(paraName + ": " + request.getParameter(paraName));

        }
        
	}
	
	@AfterReturning(pointcut = "controllerLog()", returning="retVal")
	public void after(JoinPoint joinpoint, Object retVal) {
		log.info(retVal.toString());
	}
	
	@AfterThrowing(pointcut = "controllerLog()", throwing="e")
	public void after(JoinPoint joinpoint, MyException e) {
		log.info("aspect, exception.code={}, msg={}", e.getCode(), e.getMessage());
	}
	
	

}
