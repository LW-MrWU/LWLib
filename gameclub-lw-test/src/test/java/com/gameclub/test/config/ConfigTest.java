package com.gameclub.test.config;

import com.gameclub.service.basic.service.plugin.BasePlugin;
import com.gameclub.service.basic.service.plugin.OnEnablePlugin;
import org.junit.Test;

/**
 * @author lw
 * @date 创建时间 2021/1/18 15:08
 * @description TODO
 */
public class ConfigTest {

    @Test
    public void TestConfig(){
        BasePlugin basePlugin = new BasePlugin() {
            @Override
            public boolean enable() {
                return true;
            }
        };
        ConfigTest configTest = new ConfigTest();
        basePlugin.getBaseLogService().info("1111");
    }
}
