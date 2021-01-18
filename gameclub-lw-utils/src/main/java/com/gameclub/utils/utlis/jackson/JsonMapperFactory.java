package com.gameclub.utils.utlis.jackson;

/**
 * @author lw
 * @date 创建时间 2021/1/18 14:04
 * @description TODO
 */
public class JsonMapperFactory {

    private static final JsonMapper JSON_MAPPER = new JsonMapper();

    private static final JsonMapper WITH_JSON_TYPE_INFO_MAPPER = new JsonMapper(false);

    private JsonMapperFactory() {

    }

    public static JsonMapper getJsonMapper() {
        return JSON_MAPPER;
    }

    public static JsonMapper getWithJsonTypeInfoMapper() {
        return WITH_JSON_TYPE_INFO_MAPPER;
    }
}
