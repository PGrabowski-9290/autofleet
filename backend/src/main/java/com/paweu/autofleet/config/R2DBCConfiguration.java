package com.paweu.autofleet.config;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import reactor.util.annotation.NonNull;
import reactor.util.annotation.NonNullApi;

@Configuration
@EnableR2dbcRepositories
public class R2DBCConfiguration extends AbstractR2dbcConfiguration {
    @Value("${spring.r2dbc.url}")
    private String url;
    @Override
    @NonNull
    public ConnectionFactory connectionFactory() {
        return ConnectionFactories.get(url);
    }
}
