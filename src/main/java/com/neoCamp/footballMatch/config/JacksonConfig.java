package com.neoCamp.footballMatch.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JacksonConfig {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Configura o módulo de data/hora do Java 8+
        objectMapper.registerModule(new JavaTimeModule());

        // Configura para não falhar em propriedades desconhecidas
        objectMapper.configure(
            com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
            false
        );

        // Configura para não falhar em beans vazios
        objectMapper.configure(
            SerializationFeature.FAIL_ON_EMPTY_BEANS,
            false
        );

        return objectMapper;
    }
}
