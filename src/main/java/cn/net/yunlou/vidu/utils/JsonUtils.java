/*
 *
 *     Copyright (c) 2019 - forever, javaeer All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the haoheio.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * Author: javaeer (javaeer@aliyun.com)
 *
 */

package cn.net.yunlou.vidu.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;


@Slf4j
public class JsonUtils {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static char INT_SPACE = 0x0020;

    public static ObjectMapper getInstance() {

        //忽略null对象和空字符串
        OBJECT_MAPPER.setDefaultPropertyInclusion(JsonInclude.Value.construct(JsonInclude.Include.NON_NULL, JsonInclude.Include.NON_EMPTY));

        //允许将空字符串（""）反序列化为null对象。
        OBJECT_MAPPER.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        //允许将空数组（[]）反序列化为null对象。
        OBJECT_MAPPER.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

        //允许未知字段
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //允许未加引号的字段名
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        //允许单引号
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        //允许反斜杠转义任何字符
        OBJECT_MAPPER.enable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature());

        return OBJECT_MAPPER;
    }

    private JsonUtils() {
        //拒绝外部实例
    }

    /**
     * 把对象转化为json
     *
     * @param entity
     * @return
     */
    public static String toJson(Object entity) {
        try {
            if (entity == null) {
                return "";
            }
            return getInstance().writeValueAsString(entity);
        } catch (IOException e) {
            log.error("toJson error(IOException):{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 把json字符串转化为Object
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return getInstance().readValue(json, clazz);
        } catch (JsonParseException e) {
            log.error("JSON解析异常，输入JSON：【{}】，目标类：【{}】，异常：{}", json, clazz.getName(), e.getMessage());
            throw new RuntimeException("JSON解析异常", e);
        } catch (JsonMappingException e) {
            log.error("JSON映射异常，输入JSON：【{}】，目标类：【{}】，异常：{}", json, clazz.getName(), e.getMessage());
            throw new RuntimeException("JSON映射异常", e);
        } catch (IOException e) {
            log.error("IO异常，输入JSON：【{}】，目标类：【{}】，异常：{}", json, clazz.getName(), e.getMessage());
            throw new RuntimeException("IO异常", e);
        }
    }

    /**
     * 处理泛型类型的JSON反序列化
     * @param json
     * @param typeReference new TypeReference<List<T>>>(){} 或 new TypeReference<T>(){}
     * @return
     * @param <T>
     */
    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            if (StringUtils.isBlank(json)) {
                return null;
            }
            return getInstance().readValue(json, typeReference);
        } catch (Exception e) {
            log.error("TypeReference JSON解析失败，输入JSON：【{}】，类型：【{}】，异常：{}",
                    json, typeReference.getType(), e.getMessage());
            throw new RuntimeException("TypeReference JSON解析失败", e);
        }
    }

    /**
     * 处理泛型类型的JSON反序列化
     * @param json JSON字符串
     * @param parametrized 泛型类型的原始类（如 CustomerResponse.class）
     * @param parameterClasses 泛型参数类型（如 String.class, User.class 等）
     * @return 反序列化后的对象
     */
    public static <T> T fromJson(String json, Class<?> parametrized, Class<?>... parameterClasses) {
        try {
            JavaType type = getInstance().getTypeFactory().constructParametricType(parametrized, parameterClasses);
            return getInstance().readValue(json, type);
        } catch (Exception e) {
            log.error("泛型JSON解析失败，输入JSON：【{}】，目标类：【{}】，异常：{}",
                    json, parametrized.getName(), e.getMessage());
            throw new RuntimeException("泛型JSON解析失败", e);
        }
    }

    /**
     * 把json字符串转化为Object
     *
     * @param json
     * @param collectionClass
     * @param elementClasses
     * @return
     */
    public static <T> T fromJson(String json, Class<?> collectionClass, Class<?> elementClasses) {
        try {

            JavaType javaType = getCollectionType(collectionClass, elementClasses);
            return getInstance().readValue(json, javaType);
        } catch (JsonParseException e) {
            log.error("【{}】has error(JsonParseException):{}", json, e.getMessage());
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            log.error("【{}】has error(JsonMappingException):{}", json, e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error("【{}】has error(IOException):{}", json, e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public static <T> T fromJson(String json, Type type) {
        try {
            JavaType javaType = getInstance().constructType(type);
            return getInstance().readValue(json, javaType);
        } catch (JsonParseException e) {
            log.error("【{}】has error(JsonParseException):{}", json, e.getMessage());
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            log.error("【{}】has error(JsonMappingException):{}", json, e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error("【{}】has error(IOException):{}", json, e.getMessage());
            throw new RuntimeException(e);
        }
    }


    private static JavaType getCollectionType(Class<?> collectionClass, Class<?> elementClasses) {
        return getInstance().getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    public static boolean isJson(String values) {
        if (StringUtils.isBlank(values)) {
            return true;
        }
        try {
            getInstance().writeValueAsString(values);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
    }


    /**
     * 丢弃值小于32的ASCII字符，包括制表符和换行符等 <br>
     * 参考ASCII的值 http://www.asciima.com/
     */
    public static String cleanJson(String values) {
        if (StringUtils.isEmpty(values)) {
            return values;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length(); i++) {
            char c = values.charAt(i);
            if (c < INT_SPACE) {
                sb.append(" ");
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public static void main(String[] args) {

        List<String> list = Lists.newArrayList();
        list.add("0");
        list.add("1");
        list.add("2");
        list.add("");

        log.info(JsonUtils.toJson(JsonUtils.toJson(list)));
    }


}
