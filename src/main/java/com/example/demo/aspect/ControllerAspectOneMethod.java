package com.example.demo.aspect;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.dto.ControllerProcessDTO;
import com.example.demo.util.JsonUtil;
import com.example.demo.vo.ResultVO;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class ControllerAspectOneMethod {

	@Pointcut("execution(* com.example.demo.controller.*.*(..))")
	public void logPointCut() {

	}

	@Around("logPointCut()")
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
		// LOG.debug("logPointcut " + joinPoint + "\t");
		Long start = System.currentTimeMillis();
		Long end;
		ControllerProcessDTO processDTO = null;
		try {
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();

			Map<String, String> headers = new HashMap<>();
			Enumeration headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String key = (String) headerNames.nextElement();
				String value = request.getHeader(key);
				headers.put(key, value);
			}

			processDTO = new ControllerProcessDTO();
			processDTO.setUrl(request.getRequestURL().toString());
			processDTO.setHttpMethod(request.getMethod());
			processDTO.setIp(request.getRemoteAddr());
			processDTO.setHeaders(headers);
			processDTO.setRequstParaMap(request.getParameterMap());
			processDTO.setControllerMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
			
			log.info("request begin, result={}",  JsonUtil.toJSONString(processDTO));
			
			Object result = joinPoint.proceed();

			end = System.currentTimeMillis();
			//log.error("+++++around " + joinPoint + "\tUse time : " + (end - start) + " ms!");
			
			processDTO.setPrcessTime(end - start);
			processDTO.setResultObject(result);
			
			log.info("request over ok, result={}",  JsonUtil.toJSONString(processDTO));
			
			return result;

		} catch (Throwable e) {
			end = System.currentTimeMillis();
			processDTO.setPrcessTime(end - start);
			
			log.info("request over with exception, result={}",  JsonUtil.toJSONString(processDTO));
			
			processDTO.setPrcessTime(end - start);
			//processDTO.setResultVO((ResultVO) result);
			
			throw e;

		}

	}

}
