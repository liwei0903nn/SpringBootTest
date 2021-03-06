package com.example.demo.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@PropertySource("classpath:swagger2.properties")
public class Swagger2Config {
	
	@Value(value = "${basePackage}")
	private String basePackage;
	
	@Value(value = "${title:}")
	private String title;
	
	@Value(value = "${description:}")
	private String description;
	
	@Value(value = "${termsOfServiceUrl:}")
	private String termsOfServiceUrl;
	
	@Value(value = "${version:}")
	private String version;

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage(basePackage))
				.paths(PathSelectors.any())
				.build();
	}
	
	private ApiInfo apiInfo() {
//		return new ApiInfoBuilder()
//				.title("springboot利用swagger构建api文档")
//				.description("简单优雅的restfun风格，http://blog.csdn.net/saytime")
//				.termsOfServiceUrl("http://blog.csdn.net/saytime")
//				.version("1.0")
//				.build();
		
		ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
		
//		System.out.println(title);
//		System.out.println(description);
		
		if (!StringUtils.isBlank(title)) {
			apiInfoBuilder.title(title);
		}
		
		if (!StringUtils.isBlank(description)) {
			apiInfoBuilder.description(description);
		}
		
		if (!StringUtils.isBlank(termsOfServiceUrl)) {
			apiInfoBuilder.termsOfServiceUrl(termsOfServiceUrl);
		}
		
		if (!StringUtils.isBlank(version)) {
			apiInfoBuilder.version(version);
		}
		
		return apiInfoBuilder.build();
	}
	
}
