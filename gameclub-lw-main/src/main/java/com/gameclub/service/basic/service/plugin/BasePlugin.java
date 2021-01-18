package com.gameclub.service.basic.service.plugin;

import com.gameclub.model.config.BaseLanguageConfig;
import com.gameclub.service.basic.service.config.BaseConfigService;
import com.gameclub.service.basic.service.language.BaseLanguageService;
import com.gameclub.service.basic.service.log.BaseLogService;
import com.gameclub.service.basic.service.message.BaseMessageService;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author lw
 * @date 创建时间 2021/1/16 18:36
 * @description 插件父类
 */
public abstract class BasePlugin extends JavaPlugin {

    private BaseLogService baseLogService;

    private BaseConfigService baseConfigService;

    private BaseLanguageService baseLanguageService;

    private BaseMessageService baseMessageService;

    /**
     * 基础日志服务
     * @author lw
     * @date 2021/1/16
     * @param []
     * @return com.gameclub.lw.biz.sevice.log.BaseLogService
     */
    public BaseLogService getBaseLogService() {
        return baseLogService;
    }

    /**
     * 基础配置文件服务
     * @author lw
     * @date 2021/1/18 17:35
     * @param []
     * @return com.gameclub.service.basic.service.config.BaseConfigService
     */
    public BaseConfigService getBaseConfigService() {
        return baseConfigService;
    }

    /**
     * 基础语言服务
     * @author lw
     * @date 2021/1/18 17:35
     * @param []
     * @return com.gameclub.service.basic.service.language.BaseLanguageService
     */
    public BaseLanguageService getBaseLanguageService() {
        return baseLanguageService;
    }

    /**
     * 基础消息服务
     * @author lw
     * @date 2021/1/18 17:58
     * @param []
     * @return com.gameclub.service.basic.service.message.BaseMessageService
     */
    public BaseMessageService getBaseMessageService() {
        return baseMessageService;
    }

    /**
     * 初始化服务
     * @author lw
     * @date 2021/1/16
     * @param []
     * @return void
     */
    private void initService(){
        baseLogService = new BaseLogService(this);
        baseConfigService = new BaseConfigService(this);
        baseLanguageService = new BaseLanguageService(this);
        baseMessageService = new BaseMessageService(this);
    }

    /**
     * 服务启动
     * @author lw
     * @date 2021/1/16
     * @param []
     * @return void
     */
    @Override
    public void onEnable(){
        initService();

        initTest();

        boolean flag = enable();
        if(!flag){
            BasePlugin tempPlugin = this;
            tempPlugin.setEnabled(false);
            this.baseLogService.warning("LWMcScaffold 加载失败，关闭插件");
        }
    }

    private void initTest(){

    }

    /**
     * TODO
     * @author bg392277
     * @date 2021/1/18 11:05
     * @param []
     * @return boolean
     */
    public abstract boolean enable();
}
