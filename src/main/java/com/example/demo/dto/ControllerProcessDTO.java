package com.example.demo.dto;


import java.util.Map;


import lombok.Data;

@Data
public class ControllerProcessDTO {
	
	 // 请求信息
    private String url;
    private String httpMethod;
    private String ip;
    private Map<String, String> headers;
    private Map<String, String[]>  requstParaMap;
    private String controllerMethod;
    
    // 响应信息
    private Object resultObject;
    
    
    // 处理时间  ms
    private Long  prcessTime; 

}
