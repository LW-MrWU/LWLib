package vip.gameclub.lwlib.service.plugin;

import vip.gameclub.lwlib.listener.BaseListener;
import vip.gameclub.lwlib.service.config.BaseConfigService;
import vip.gameclub.lwlib.service.database.mysql.BaseMysqlService;
import vip.gameclub.lwlib.service.log.BaseLogService;
import vip.gameclub.lwlib.service.utils.BasePlayerService;
import vip.gameclub.lwlib.service.utils.BaseStringService;
import vip.gameclub.lwlib.model.command.BaseCommand;
import vip.gameclub.lwlib.model.enumModel.BaseSysMsgEnum;
import vip.gameclub.lwlib.service.language.BaseLanguageService;
import vip.gameclub.lwlib.service.message.BaseMessageService;
import org.bukkit.Server;
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

    //utils
    private BaseStringService baseStringService;
    private BasePlayerService basePlayerService;

    //mysql
    private BaseMysqlService baseMysqlService;

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
        baseStringService = new BaseStringService(this);
        basePlayerService = new BasePlayerService(this);

        baseMysqlService = new BaseMysqlService(this);
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
            this.baseLogService.warningByLanguage(BaseSysMsgEnum.FAIL_LOAD.name(), BaseSysMsgEnum.FAIL_LOAD.getValue());
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

    /**
     * 注册监听
     * @author lw
     * @date 2021/1/24
     * @param [listener]
     * @return void
     */
    public <T extends BaseListener> void registerListener(T listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    /**
     * 获取服务server
     * @author lw
     * @date 2021/1/25 11:42
     * @param []
     * @return org.bukkit.Server
     */
    public Server getBaseServer(){
        return this.getServer();
    }

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
     * 字符串公共服务
     * @author lw
     * @date 2021/1/19 14:03
     * @param []
     * @return BaseStringService
     */
    public BaseStringService getBaseStringService() {
        return baseStringService;
    }

    /**
     * 玩家公共服务
     * @author lw
     * @date 2021/1/19 14:03
     * @param []
     * @return BaseStringService
     */
    public BasePlayerService getBasePlayerService() {
        return basePlayerService;
    }

    /**
     * 基础数据库服务
     * @author lw
     * @date 2021/1/25 14:48
     * @param []
     * @return BaseMysqlService
     */
    public BaseMysqlService getBaseMysqlService() {
        return baseMysqlService;
    }

}
