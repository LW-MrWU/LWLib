package com.gameclub.service.basic.service.plugin;

import com.gameclub.model.config.BaseLanguageConfig;
import com.gameclub.model.language.BaseLanguageEnum;

/**
 * @author lw
 * @date 创建时间 2021/1/16 18:36
 * @description 服务启动时调用
 */
public class MainPlugin extends BasePlugin {
    private static MainPlugin mainPlugin;

    public static MainPlugin getInstance(){
        return mainPlugin;
    }

    @Override
    public boolean enable() {
        mainPlugin = this;

        //初始化配置文件
        initConfig();
        //成功加载提示
        String successLoad = getBaseLanguageService().getLanguage(BaseLanguageEnum.SUCCESS_LOAD.name(), BaseLanguageEnum.SUCCESS_LOAD.getValue());
        getBaseLogService().info(successLoad);

        //测试下
        initTest();
        return true;
    }

    /**
     * 初始化配置文件
     * @author lw
     * @date 2021/1/19 14:11
     * @param []
     * @return void
     */
    private void initConfig(){
        //语言配置文件
        BaseLanguageConfig baseLanguageConfig = new BaseLanguageConfig(this);
        getBaseConfigService().registerConfig(baseLanguageConfig);
    }

    private void initTest(){
    }
}
