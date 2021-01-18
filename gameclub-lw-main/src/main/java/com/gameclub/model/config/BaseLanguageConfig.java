package com.gameclub.model.config;

import com.gameclub.service.basic.service.plugin.BasePlugin;

/**
 * @author lw
 * @date 创建时间 2021/1/18 16:38
 * @description 语言配置类
 */
public class BaseLanguageConfig extends BaseConfig{
    public static String configName = "language/lang.yml";

    private String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    /**
     * 构造函数
     *
     * @param fileName
     * @param basePlugin
     * @return
     * @author lw
     * @date 2021/1/18 14:51
     */
    public BaseLanguageConfig(BasePlugin basePlugin) {
        super(BaseLanguageConfig.configName, basePlugin);
    }

    @Override
    protected boolean createConfig() {
        return false;
    }

    @Override
    public void loadConfig() {

    }
}
