package com.gameclub.service.basic.service.plugin;

import com.gameclub.model.config.BaseLanguageConfig;
import com.gameclub.model.language.BaseLanguageEnum;

/**
 * @author lw
 * @date 创建时间 2021/1/16 18:36
 * @description 服务启动时调用
 */
public class MainPlugin extends BasePlugin {
    @Override
    public boolean enable() {
        //初始化配置
        initConfig();
        //成功加载提示
        String successMsg = getBaseUtilsService().translateColorCodes("&a成功加载 LWMcScaffold");
        getBaseLogService().info(successMsg);

        //测试下
        initTest();
        return true;
    }

    private void initConfig(){
        BaseLanguageConfig baseLanguageConfig = new BaseLanguageConfig(this);
        getBaseConfigService().registerConfig(baseLanguageConfig);
    }

    private void initTest(){
        BaseLanguageEnum errora = BaseLanguageEnum.COMMAND_ARGS_ERROR;
        BaseLanguageEnum noPer = BaseLanguageEnum.COMMAND_PERMISSIONDENIEDMESSAGE;
        String error = getBaseLanguageService().getLanguage(errora.name(), errora.getValue());
        String a = getBaseLanguageService().getLanguage(noPer.name(), noPer.getValue(), "啥呀");
        getBaseLogService().warning("error lang: "+error);
        getBaseLogService().warning("a: "+a);
    }
}
