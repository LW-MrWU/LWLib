package vip.gameclub.lwlib.model.enumModel;

/**
 * 语言配置枚举类
 * @author LW-MrWU
 * @date 创建时间 2021/1/22 16:18
 */
public enum BaseLanguageEnum {
    /**
     * 中文简体
     */
    zh_CN("zh_CN.yml"),
    /**
     * 中文繁体
     */
    zh_FT("zh_FT.yml"),
    /**
     * 英语
     */
    en_US("en_US.yml");

    private String value;

    BaseLanguageEnum(String val){
        this.value = val;
    }

    /**
     * 获取value值
     * @param
     * @return java.lang.String
     * @author LW-MrWU
     * @date 2021/1/28 11:39
     */
    public String getValue() {
        return value;
    }

    /**
     * 根据enumname 返回enum
     * @param enumName
     * @return vip.gameclub.lwlib.model.enumModel.BaseLanguageEnum
     * @author LW-MrWU
     * @date 2021/1/28 11:39
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
