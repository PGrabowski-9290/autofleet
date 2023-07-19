package com.paweu.autofleet.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Optional;

public class VersionHolder {
    private final String version;

    public VersionHolder(ApplicationContext context){
        version = context.getBeansWithAnnotation(SpringBootApplication.class).entrySet().stream()
                .findFirst()
                .flatMap(s -> {
                    final String implementationVersion = s.getValue().getClass().getPackage().getImplementationVersion();
                    return Optional.ofNullable(implementationVersion);
                }).orElse("dev");
    }

    public String getVersion(){
        return version;
    }


}
