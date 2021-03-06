package com.imooc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    public CorsConfig(){

    }

    @Bean
    public CorsFilter corsFilter(){
        // 1.添加cors配置信息
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:8080");

        // 设置是否发送cookie信息
        config.setAllowCredentials(true);
        // 设置允许的请求的方式
        config.addAllowedMethod("*");
        // 设置允许的header
        config.addAllowedHeader("*");

        // 2.为url配置映射的路径
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", config);


        // 3.返回重新定义好的corsSource
        return new CorsFilter(corsConfigurationSource);

    }

}
