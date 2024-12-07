package com.linkun.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> T toObject(InputStream stream, Class<T> clazz) {
        try {
            return mapper.readValue(stream, clazz);
        } catch (IOException e) {
            logger.error("Exception", e);
        }
        return null;
    }

    public static <T> T toObject(String string, Class<T> clazz) {
        try {
            return mapper.readValue(string, clazz);
        } catch (IOException e) {
            logger.error("Exception", e);
        }
        return null;
    }

    public static <T> String toJson(T t) {
        try {
            return mapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            logger.error("Exception", e);
        }
        return null;
    }

    public static <T> Map<String, T> toMap(String jsonAsString) {
        try {
            return mapper.readValue(jsonAsString, new TypeReference<Map<String, T>>() {
            });
        } catch (Exception e) {
            logger.error("parse jsonString error.[" + jsonAsString + "]");
        }
        return null;
    }

    public static <T> Map<String, T>[] toMapArray(String jsonAsString) {
        try {
            return mapper.readValue(jsonAsString, new TypeReference<Map<String, T>[]>() {
            });
        } catch (Exception e) {
            logger.error("parse jsonString error.[" + jsonAsString + "]");
        }
        return null;
    }

    public static <T> T toList(String string, Class<?> clazzList, Class<?> clazzBean) {
        try {
            JavaType javaType = getCollectionType(clazzList, clazzBean);
            return mapper.readValue(string, javaType);
        } catch (Exception e) {
            logger.error("Exception", e);
        }
        return null;
    }

    public static <T> T toList(String string, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(string, typeReference);
        } catch (Exception e) {
            logger.error("Exception", e);
        }
        return null;
    }

    public static <T> T toListWithLogger(String string, Class<?> clazzList, Class<?> clazzBean, String supplementInfo) {
        try {
            JavaType javaType = getCollectionType(clazzList, clazzBean);
            return mapper.readValue(string, javaType);
        } catch (IOException e) {
            logger.error("Exception... supplementInfo:[{}]", supplementInfo, e);
        }
        return null;
    }

    public static <T> T toObjectWithLog(String string, Class<T> clazz, String supplementInfo) {
        try {
            return mapper.readValue(string, clazz);
        } catch (IOException e) {
            logger.error("Exception... supplementInfo:[{}]", supplementInfo, e);
        }
        return null;
    }

    private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    /**
     * 
     * 功能描述: <br>
     * 分隔符分隔的字符串转json(数组)格式字符串
     *
     */
    public static String convertToJson(String string, String formatChar) {
        if (StringUtils.isNotEmpty(string) && StringUtils.isNotEmpty(formatChar)) {
            String[] array = string.split(formatChar);
            if (ArrayUtils.isNotEmpty(array)) {
                return JsonUtil.toJson(array);
            }
        }
        return null;
    }
}
