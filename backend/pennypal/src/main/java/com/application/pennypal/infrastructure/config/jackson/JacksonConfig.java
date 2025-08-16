package com.application.pennypal.infrastructure.config.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JacksonConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer addJavaTimeModule() {
        return builder -> builder.modules(new JavaTimeModule());
    }
        @Bean
        @Primary
        public ObjectMapper objectMapper() {
            return JsonMapper.builder()
                    .addModule(new JavaTimeModule())
                    .addModule(new Jdk8Module())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .build();
}
}
