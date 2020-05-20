package com.cloud.server.common.config;

import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class JavaTimeConverterConfiguration {
    @Bean
    public FeignFormatterRegistrar localDataTimeFormatRegister() {
        return registry -> registry.addConverter(new LocalDateTime2StringConverter());
    }

    @Bean
    public FeignFormatterRegistrar localDataFormatRegister() {
        return registry -> registry.addConverter(new LocalDate2StringConverter());
    }


    private class LocalDateTime2StringConverter implements Converter<LocalDateTime, String> {
        @Override
        public String convert(@NonNull LocalDateTime source) {
            return source.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
    }

    private class LocalDate2StringConverter implements Converter<LocalDate, String> {
        @Override
        public String convert(@NonNull LocalDate source) {
            return source.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
    }
}
