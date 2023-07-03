package com.ransankul.clickmart.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Your API Title", version = "1.0.0"))
public class SwaggerConfig implements WebMvcConfigurer {

    static {
        SpringDocUtils.getConfig().addAnnotationsToIgnore(JsonIgnore.class);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springdoc-openapi-ui/")
                .resourceChain(false);
    }
}

