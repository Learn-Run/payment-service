package com.unionclass.paymentservice.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.stereotype.Component;

@Component
public class JsonMapperImpl implements JsonMapper {

    private final ObjectMapper objectMapper;

    public JsonMapperImpl() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new ParameterNamesModule());
        this.objectMapper.registerModule(new Jdk8Module());
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public <T> T convert(Object source, Class<T> targetType) {
        return objectMapper.convertValue(source, targetType);
    }

    @Override
    public <T> T convert(Object source, TypeReference<T> typeReference) {

        return objectMapper.convertValue(source, typeReference);
    }

    @Override
    public <T> T readValue(String json, Class<T> targetType) {

        try {

            return objectMapper.readValue(json, targetType);

        } catch (Exception e) {

            throw new RuntimeException("JSON 파싱 실패: " + e.getMessage(), e);
        }
    }
}
