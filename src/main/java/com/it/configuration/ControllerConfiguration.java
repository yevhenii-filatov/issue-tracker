package com.it.configuration;

import com.it.converter.QueryObjectConverterFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Yevhenii Filatov
 * @since 3/28/23
 */

@Configuration
@ComponentScan
public class ControllerConfiguration implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new QueryObjectConverterFactory<>());
    }
}
