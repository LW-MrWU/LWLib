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
 * 插件启动类父类服务
 * @author LW-MrWU
 * @date 创建时间 2021/1/16 18:36
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
     * @param
     * @return void
     * @author LW-MrWU
     * @date 2021/1/28 12:07
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
     * @param
     * @return void
     * @author LW-MrWU
     * @date 2021/1/28 12:07
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
     * @param
     * @return void
     * @author LW-MrWU
     * @date 2021/1/28 12:07
     */
    @Override
    public void onDisable(){
        disable();
    }

    /**
     * 自定义服务启动拓展
     * @param
     * @return boolean
     * @author LW-MrWU
     * @date 2021/1/28 12:07
     */
    public abstract boolean enable();

    /**
     * 自定义服务卸载拓展
     * @param
     * @return boolean
     * @author LW-MrWU
     * @date 2021/1/28 12:08
     */
    public abstract boolean disable();

    /**
     * 注册命令
     * @param baseCommand 主命令实体
     * @return void
     * @author LW-MrWU
     * @date 2021/1/28 12:08
     */
    public <T extends BaseCommand> void registerCommand(T baseCommand) {
        PluginCommand pluginCommand = getCommand(baseCommand.getCommandName());
        pluginCommand.setExecutor(baseCommand);
        pluginCommand.setTabCompleter(baseCommand);
    }

    /**
     * 注册监听
     * @param listener 监听实体
     * @return void
     * @author LW-MrWU
     * @date 2021/1/28 12:08
     */
    public <T extends BaseListener> void registerListener(T listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    /**
     * 获取服务server
     * @param
     * @return org.bukkit.Server
     * @author LW-MrWU
     * @date 2021/1/28 12:09
     */
    public Server getBaseServer(){
        return this.getServer();
    }

    /**
     * 获取基础日志服务
     * @param
     * @return vip.gameclub.lwlib.service.log.BaseLogService
     * @author LW-MrWU
     * @date 2021/1/28 12:09
     */
    public BaseLogService getBaseLogService() {
        return baseLogService;
    }

    /**
     * 获取基础配置文件服务
     * @param
     * @return vip.gameclub.lwlib.service.config.BaseConfigService
     * @author LW-MrWU
     * @date 2021/1/28 12:09
     */
    public BaseConfigService getBaseConfigService() {
        return baseConfigService;
    }

    /**
     * 获取基础语言服务
     * @param
     * @return vip.gameclub.lwlib.service.language.BaseLanguageService
     * @author LW-MrWU
     * @date 2021/1/28 12:09
     */
    public BaseLanguageService getBaseLanguageService() {
        return baseLanguageService;
    }

    /**
     * 获取基础消息服务
     * @param
     * @return vip.gameclub.lwlib.service.message.BaseMessageService
     * @author LW-MrWU
     * @date 2021/1/28 12:10
     */
    public BaseMessageService getBaseMessageService() {
        return baseMessageService;
    }

    /**
     * 获取基础字符串公共服务
     * @param
     * @return vip.gameclub.lwlib.service.utils.BaseStringService
     * @author LW-MrWU
     * @date 2021/1/28 12:10
     */
    public BaseStringService getBaseStringService() {
        return baseStringService;
    }

    /**
     * 获取玩家公共服务
     * @param
     * @return vip.gameclub.lwlib.service.utils.BasePlayerService
     * @author LW-MrWU
     * @date 2021/1/28 12:10
     */
    public BasePlayerService getBasePlayerService() {
        return basePlayerService;
    }

    /**
     * 获取基础mysql服务
     * @param
     * @return vip.gameclub.lwlib.service.database.mysql.BaseMysqlService
     * @author LW-MrWU
     * @date 2021/1/28 12:11
     */
    public BaseMysqlService getBaseMysqlService() {
        return baseMysqlService;
    }

}
