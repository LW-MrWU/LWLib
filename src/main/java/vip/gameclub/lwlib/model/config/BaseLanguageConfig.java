package vip.gameclub.lwlib.model.config;

import vip.gameclub.lwlib.service.plugin.BasePlugin;

/**
 * 语言配置父类
 * @author LW-MrWU
 * @date 创建时间 2021/1/18 16:38
 */
public class BaseLanguageConfig extends BaseConfig {

    /**
     * 构造函数
     * @param basePlugin 启动主类
     * @param fileName 配置文件名
     * @return
     * @author LW-MrWU
     * @date 2021/1/28 11:36
     */
    public BaseLanguageConfig(BasePlugin basePlugin, String fileName) {
        super(basePlugin, fileName, "languages");
        setConfigName(basePlugin, this);
    }

    @Override
    public void loadConfig() {
    }

    @Override
    protected boolean createConfig() {
        return false;
    }
}
