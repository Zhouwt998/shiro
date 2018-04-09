package com.zgtech.edu.basicframework.kernel.configure;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErrorPageConfigure {
    @Bean
    public ErrorViewResolver errorPageRegistrar(){
        return new MyErrorPageRegistrar();
    }
}
