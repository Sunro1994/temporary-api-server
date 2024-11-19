package com.ja.finalproject.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ja.finalproject.global.interceptor.SessionInterceptor;

@Configuration
@RequiredArgsConstructor
public class AppConfig implements WebMvcConfigurer {

    private final SessionInterceptor sessionInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploadFiles/**")
            .addResourceLocations("file:///C:/uploadFiles/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor)
            .addPathPatterns("/board/**")
            .excludePathPatterns("/board/mainPage", "/board/articleDetailPage")
            // .addPathPatterns("/board/writeArticlePage")
            // .addPathPatterns("/board/writeArticleProcess")
            // .addPathPatterns("/board/updateArticlePage")
            // .addPathPatterns("/board/updateArticleProcess")
        ;
    }

}
