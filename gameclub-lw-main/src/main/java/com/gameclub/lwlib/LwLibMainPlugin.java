package com.gameclub.lwlib;

import com.gameclub.model.enumModel.BaseSysMsgEnum;
import com.gameclub.service.basic.service.plugin.BasePlugin;

/**
 * @author lw
 * @date 创建时间 2021/1/16 18:36
 * @description lwlib服务启动时调用
 */
public class LwLibMainPlugin extends BasePlugin {
    private static LwLibMainPlugin lwLibMainPlugin;

    public static LwLibMainPlugin getInstance(){
        return lwLibMainPlugin;
    }

    @Override
    public boolean enable() {
        lwLibMainPlugin = this;

        //初始化配置文件
        initConfig();
        //成功加载提示
        String successLoad = getBaseLanguageService().getLanguage(BaseSysMsgEnum.SUCCESS_LOAD.name(), BaseSysMsgEnum.SUCCESS_LOAD.getValue());
        getBaseLogService().info(successLoad);
        return true;
    }

    @Override
    public boolean disable() {
        return false;
    }

    /**
     * 初始化配置文件
     * @author lw
     * @date 2021/1/19 14:11
     * @param []
     * @return void
     */
    private void initConfig(){
        LwLibDefaultConfig defaultConfig = new LwLibDefaultConfig();
    }
}