package vip.gameclub.lwlib.service.plugin;

import org.bukkit.plugin.ServicePriority;
import vip.gameclub.lwlib.event.BaseEvent;
import vip.gameclub.lwlib.listener.BaseListener;
import vip.gameclub.lwlib.model.scoreboard.BaseScoreboard;
import vip.gameclub.lwlib.service.BaseService;
import vip.gameclub.lwlib.service.config.BaseConfigService;
import vip.gameclub.lwlib.service.database.mysql.BaseMysqlService;
import vip.gameclub.lwlib.service.log.BaseLogService;
import vip.gameclub.lwlib.model.command.BaseCommand;
import vip.gameclub.lwlib.model.enumModel.BaseSysMsgEnum;
import vip.gameclub.lwlib.service.language.BaseLanguageService;
import vip.gameclub.lwlib.service.message.BaseMessageService;
import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import vip.gameclub.lwlib.service.scoreboard.BaseScoreboardService;

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

    private BaseScoreboardService baseScoreboardService;

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
        baseScoreboardService = new BaseScoreboardService(this);

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
     * 触发自定义事件
     * @param customEvent 自定义事件实体
     * @return void
     * @author LW-MrWU
     * @date 2021/1/31 15:45
     */
    public <T extends BaseEvent> void callCustomEvent(T customEvent){
        getServer().getPluginManager().callEvent(customEvent);
    }

    /**
     * 注册服务
     * @param service 服务
     * @return void
     * @author LW-MrWU
     * @date 2021/1/31 15:46
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <T extends BaseService> void registerService(T service) {
        Class serviceClass = service.getClass();
        getServer().getServicesManager().register(serviceClass, service, this, ServicePriority.Normal);
    }

    /**
     * 注册服务
     * @param cls 服务的类
     * @param service 服务
     * @param <T> 服务类型
     * @param <D> 服务类型
     * @return void
     * @author LW-MrWU
     * @date 2021/1/31 15:47
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <T extends BaseService,D extends T> void registerService(Class<T> cls, D service) {
        getServer().getServicesManager().register(cls, service, this, ServicePriority.Normal);
    }

    /**
     * 注册记分板
     * @param scoreboard
     * @return void
     * @author LW-MrWU
     * @date 2021/2/3 15:05
     */
    public <T extends BaseScoreboard> void registerScoreboard(T scoreboard) {
        getBaseScoreboardService().addScoreboard(scoreboard);
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
     * 获取基础计分板服务
     * @param
     * @return vip.gameclub.lwlib.service.scoreboard.BaseScoreboardService
     * @author LW-MrWU
     * @date 2021/2/3 15:04
     */
    public BaseScoreboardService getBaseScoreboardService() {
        return baseScoreboardService;
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
