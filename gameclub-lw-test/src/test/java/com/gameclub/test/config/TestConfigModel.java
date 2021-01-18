package com.gameclub.test.config;

import com.gameclub.model.config.BaseConfig;
import com.gameclub.service.basic.service.plugin.BasePlugin;

/**
 * @author lw
 * @date 创建时间 2021/1/18 15:09
 * @description TODO
 */
public class TestConfigModel extends BaseConfig {
    private static String configName = "test.yml";

    /**
     * 构造函数
     *
     * @param fileName
     * @param plugin
     * @return
     * @author lw
     * @date 2021/1/18 14:51
     */
    public TestConfigModel(String fileName, BasePlugin basePlugin) {
        super(TestConfigModel.configName, basePlugin);
    }

    @Override
    protected boolean createConfig() {
        return true;
    }

    @Override
    public void loadConfig() {

    }
}
