package com.shopping.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI shoppingApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("線上購物系統 API 文件")
                        .description("提供商品管理、訂單建立等功能的後端 API")
                        .version("v1.0.0"));
    }
}