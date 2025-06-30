package com.unionclass.paymentservice.common.util;

import com.fasterxml.jackson.core.type.TypeReference;

public interface JsonMapper {

    <T> T convert(Object source, Class<T> targetType);

    <T> T convert(Object source, TypeReference<T> typeReference);

    <T> T readValue(String json, Class<T> targetType);
}
