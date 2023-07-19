package com.paweu.autofleet.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    VersionHolder getVersionHolder(ApplicationContext context){
        return new VersionHolder(context);
    }
}
