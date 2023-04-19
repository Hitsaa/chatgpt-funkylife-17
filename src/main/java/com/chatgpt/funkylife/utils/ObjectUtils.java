package com.chatgpt.funkylife.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.chatgpt.funkylife.exception.AppException;
import org.springframework.http.HttpStatus;

import java.util.Collection;

public class ObjectUtils {

    public static <T> T convertObjectToClassWithCollection(Object object, Class<?> elementClass, Class<? extends Collection> collectionClass) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.convertValue(object, objectMapper.getTypeFactory().constructCollectionType(collectionClass, elementClass));
        } catch (Exception e) {
            throw new AppException(ErrorMessage.MALFORMED_REQUEST, HttpStatus.BAD_REQUEST);
        }
    }

    public static String convertObjectToString(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new AppException(ErrorMessage.MALFORMED_REQUEST, HttpStatus.BAD_REQUEST);
        }
    }

    public static <T> T convertStringToObject(String string, Class<?> elementClass, Class<? extends Collection> collectionClass) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(string, objectMapper.getTypeFactory().constructCollectionType(collectionClass, elementClass));
        } catch (JsonProcessingException e) {
            throw new AppException(ErrorMessage.MALFORMED_REQUEST, HttpStatus.BAD_REQUEST);
        }
    }

}
