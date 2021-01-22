package com.gameclub.lwlib.service.basic.service.plugin;

import com.gameclub.lwlib.service.basic.service.config.BaseConfigService;
import com.gameclub.lwlib.service.basic.service.log.BaseLogService;
import com.gameclub.lwlib.service.basic.service.utils.BaseUtilsService;
import com.gameclub.lwlib.model.command.BaseCommand;
import com.gameclub.lwlib.model.enumModel.BaseSysMsgEnum;
import com.gameclub.lwlib.service.basic.service.language.BaseLanguageService;
import com.gameclub.lwlib.service.basic.service.message.BaseMessageService;
import org.bukkit.command.PluginCommand;
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

    private BaseUtilsService baseUtilsService;

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
     * @return BaseConfigService
     */
    public BaseConfigService getBaseConfigService() {
        return baseConfigService;
    }

    /**
     * 基础语言服务
     * @author lw
     * @date 2021/1/18 17:35
     * @param []
     * @return BaseLanguageService
     */
    public BaseLanguageService getBaseLanguageService() {
        return baseLanguageService;
    }

    /**
     * 基础消息服务
     * @author lw
     * @date 2021/1/18 17:58
     * @param []
     * @return BaseMessageService
     */
    public BaseMessageService getBaseMessageService() {
        return baseMessageService;
    }

    /**
     * 基础公共服务
     * @author lw
     * @date 2021/1/19 14:03
     * @param []
     * @return BaseUtilsService
     */
    public BaseUtilsService getBaseUtilsService() {
        return baseUtilsService;
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
        baseUtilsService = new BaseUtilsService(this);
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
        //初始化服务
        initService();

        boolean flag = enable();

        if(!flag){
            BasePlugin tempPlugin = this;
            tempPlugin.setEnabled(false);
            String failLoad = baseLanguageService.getLanguage(BaseSysMsgEnum.FAIL_LOAD.name(), BaseSysMsgEnum.FAIL_LOAD.getValue());
            this.baseLogService.warning(failLoad);
        }
    }

    /**
     * 服务卸载
     * @author lw
     * @date 2021/1/22 9:58
     * @param []
     * @return void
     */
    @Override
    public void onDisable(){
        disable();
    }

    /**
     * 自定义服务启动
     * @author bg392277
     * @date 2021/1/18 11:05
     * @param []
     * @return boolean
     */
    public abstract boolean enable();

    /**
     * 自定义服务卸载
     * @author lw
     * @date 2021/1/22 9:58
     * @param []
     * @return boolean
     */
    public abstract boolean disable();

    /**
     * 注册命令执行者
     * @author lw
     * @date 2021/1/19 15:32
     * @param [commandServcie 命令执行服务]
     * @return void
     */
    public <T extends BaseCommand> void registerCommand(T baseCommand) {
        PluginCommand pluginCommand = getCommand(baseCommand.getCommandName());
        pluginCommand.setExecutor(baseCommand);
        pluginCommand.setTabCompleter(baseCommand);
    }
}
