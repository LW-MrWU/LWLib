package com.gameclub.lwlib.model.config;

import com.gameclub.lwlib.service.basic.service.plugin.BasePlugin;

/**
 * @author lw
 * @date 创建时间 2021/1/18 16:38
 * @description 语言配置父类
 */
public class BaseLanguageConfig extends BaseConfig {

    /**
     * 构造函数
     *
     * @param fileName
     * @param basePlugin
     * @return
     * @author lw
     * @date 2021/1/18 14:51
     */
    public BaseLanguageConfig(BasePlugin basePlugin, String fileName) {
        super(basePlugin, fileName, "languages");
    }

    @Override
    public void loadConfig() {
    }

    @Override
    protected boolean createConfig() {
        return false;
    }
}
