package com.shopping.system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
    	    //允許所有路徑
        registry.addMapping("/**")
        			// 允許所有來源 (Production 需要修改限定 Domain)
                .allowedOriginPatterns("*")
                // 允許所有方法、Header 與憑證
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}