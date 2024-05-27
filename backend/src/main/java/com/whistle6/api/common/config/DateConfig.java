package com.whistle6.api.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.DateFormatter;

@Configuration
public class DateConfig {
    @Bean
    public DateFormatter defaultDateFormatter() {
        return new DateFormatter("yyyy-MM-dd HH:mm:ss");
    }
}
