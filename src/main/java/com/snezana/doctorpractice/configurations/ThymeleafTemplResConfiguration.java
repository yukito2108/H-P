package com.snezana.doctorpractice.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * Creates ClassLoaderTemplateResolver bean that resolves html template
 */
@Configuration
public class ThymeleafTemplResConfiguration {
	
	@Bean
	public ClassLoaderTemplateResolver templateResolver() {
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setPrefix("resources/");
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setSuffix(".html");		
		templateResolver.setCharacterEncoding("UTF-8");	
		return templateResolver;
	}
}
