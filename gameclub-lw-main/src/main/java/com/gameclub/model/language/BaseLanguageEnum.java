package com.gameclub.model.language;

/**
 * @author lw
 * @date 创建时间 2021/1/18 16:42
 * @description TODO
 */
public enum BaseLanguageEnum {

    //无权限提醒
    COMMAND_PERMISSIONDENIEDMESSAGE("&4您没有权限执行:{0}."),
    //不属于命令执行范围，如玩家无法执行控制台命令，控制台无法执行玩家的命令
    COMMAND_NOCOMMANDSENDERTYPEMESSAGE("&4该命令不能在此处执行."),
    //参数列表不正确
    COMMAND_ARGS_ERROR("&4参数列表不正确."),
    //命令未找到
    COMMAND_NOT_FOUND_COMMAND("&4命令未找到.");

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
