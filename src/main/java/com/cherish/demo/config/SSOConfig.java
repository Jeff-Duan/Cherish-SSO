package com.cherish.demo.config;

import com.cherish.demo.filter.SSOFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class SSOConfig {

    /**
     * 配置过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean FilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(SSOFilter());
        registration.addUrlPatterns("/*");
        registration.setName("SSOFilter");
        return registration;
    }

    /**
     * 创建一个bean
     * @return
     */
    @Bean(name = "SSOFilter")
    public Filter SSOFilter() {
        return new SSOFilter();
    }


}
