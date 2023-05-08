/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.TPSEO.config;

import java.util.Collections;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

/**
 *
 * @author Ari
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // ...

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/assets/**")
                .addResourceLocations("classpath:/static/assets/")
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.DAYS));
    }

//    @Bean
//    public ResourceHttpRequestHandler resourceHttpRequestHandler() {
//        ResourceHttpRequestHandler handler = new ResourceHttpRequestHandler();
//        handler.setLocations(Collections.singletonList(new ClassPathResource("static/")));
//        handler.setCacheControl(CacheControl.maxAge(30, TimeUnit.DAYS));
//        handler.setHttpRequestHandlers(Collections.singletonList(new CachingResourceHttpRequestHandler()));
//        return handler;
//    }
}
