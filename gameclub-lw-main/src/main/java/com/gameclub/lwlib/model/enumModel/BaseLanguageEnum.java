package com.gameclub.lwlib.model.enumModel;

/**
 * @author lw
 * @date 创建时间 2021/1/22 16:18
 * @description 语言配置枚举类
 */
public enum BaseLanguageEnum {
    zh_CN("zh_CN.yml"),
    zh_FT("zh_FT.yml"),
    en_US("en_US.yml");

    private String value;

    BaseLanguageEnum(String val){
        this.value = val;
    }

    public String getValue() {
        return value;
    }

    /**
     * 根据enumname 返回enum
     * @author lw
     * @date 2021/1/18 16:46
     * @param [enumName]
     * @return com.gameclub.model.language.BaseLanguageEnum
     */
    public static BaseLanguageEnum getEnumByName(String enumName) {
        BaseLanguageEnum result = null;
        BaseLanguageEnum[] values = BaseLanguageEnum.values();
        for(int i=0;i<values.length;i++) {
            BaseLanguageEnum tempEnum = values[i];
            if(tempEnum.name().equals(enumName)) {
                result = tempEnum;
                break;
            }
        }
        return result;
    }
}
