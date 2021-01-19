package com.gameclub.model.language;

/**
 * @author lw
 * @date 创建时间 2021/1/18 16:42
 * @description TODO
 */
public enum BaseLanguageEnum {

    //无权限提醒
    COMMAND_PERMISSIONDENIEDMESSAGE("&4You do not have permission exec to:{0}."),
    //不属于命令执行范围，如玩家无法执行控制台命令，控制台无法执行玩家的命令
    COMMAND_NOCOMMANDSENDERTYPEMESSAGE("&4Your terminal does not meet this requirement."),
    //参数列表不正确
    COMMAND_ARGS_ERROR("&4Command parameter format is incorrect."),
    //命令未找到
    COMMAND_NOT_FOUND_COMMAND("&4Command not found.");

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
