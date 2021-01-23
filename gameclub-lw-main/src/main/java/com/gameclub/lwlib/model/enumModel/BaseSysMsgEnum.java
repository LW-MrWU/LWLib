package com.gameclub.lwlib.model.enumModel;

/**
 * @author lw
 * @date 创建时间 2021/1/18 16:42
 * @description 系统提示
 */
public enum BaseSysMsgEnum {

    //无权限提醒
    COMMAND_NO_PERMISSION("&cYou do not have permission exec to:{0}."),
    //不属于命令执行范围，如玩家无法执行控制台命令，控制台无法执行玩家的命令
    COMMAND_SENDERTYPE_ERROR("&cYour terminal does not meet this requirement."),
    //成功加载插件
    SUCCESS_LOAD("&aplugin load success."),
    //加载插件失败
    FAIL_LOAD("&cplugin load failure."),
    //配置文件未找到
    CONFIG_NOT_FOUND("&bFile:{0} not found! Creating a new one"),
    //配置重载成功
    CONFIG_RELOAD_SUCCESS("&aConfig:{0} reload success."),
    //配置文件保存异常
    CONFIG_SAVE_EXCEPTION("&cCould not save config to {0} exception:{1}");

    private String value;

    BaseSysMsgEnum(String val){
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
    public static BaseSysMsgEnum getEnumByName(String enumName) {
        BaseSysMsgEnum result = null;
        BaseSysMsgEnum[] values = BaseSysMsgEnum.values();
        for(int i=0;i<values.length;i++) {
            BaseSysMsgEnum tempEnum = values[i];
            if(tempEnum.name().equals(enumName)) {
                result = tempEnum;
                break;
            }
        }
        return result;
    }
}
