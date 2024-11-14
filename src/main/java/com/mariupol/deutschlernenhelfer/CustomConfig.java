package com.mariupol.deutschlernenhelfer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

@Configuration
@PropertySource("classpath:deutsch.properties")
public class CustomConfig {
    @Bean
    public Locale locale() {
        return Locale.getDefault();
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:deutsch");
        messageSource.setCacheSeconds(3600);
        return messageSource;
    }
}

