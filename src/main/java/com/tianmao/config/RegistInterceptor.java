package com.tianmao.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author 胡建德
 */
@Configuration
public class RegistInterceptor extends WebMvcConfigurerAdapter {
    @Bean
    public LoginInterceptor getLoginIntercepter() {
        return new LoginInterceptor();
    }

    @Bean
    public OtherInterceptor getOtherInterceptor() {
        return new OtherInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(getLoginIntercepter())
                .addPathPatterns("/**");
        registry.addInterceptor(getOtherInterceptor()).addPathPatterns("/**");
    }
}
