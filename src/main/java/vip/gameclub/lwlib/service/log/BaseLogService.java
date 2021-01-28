package vip.gameclub.lwlib.service.log;

import vip.gameclub.lwlib.service.plugin.BasePlugin;

/**
 * 基础日志服务
 * @author LW-MrWU
 * @date 创建时间 2021/1/16 18:13
 */
public class BaseLogService {
    protected BasePlugin basePlugin;

    /**
     * 构造函数
     * @param basePlugin 启动主类
     * @return
     * @author LW-MrWU
     * @date 2021/1/28 12:02
     */
    public BaseLogService(BasePlugin basePlugin){
        this.basePlugin = basePlugin;
    }

    /**
     * 打印info日志
     * @param log 日志
     * @return void
     * @author LW-MrWU
     * @date 2021/1/28 12:02
     */
    public void info(String log){
        String reLog = this.basePlugin.getBaseStringService().chatColorCodes(log);
        this.basePlugin.getLogger().info(reLog);
    }

    /**
     * 打印warning日志
     * @param log 日志
     * @return void
     * @author LW-MrWU
     * @date 2021/1/28 12:02
     */
    public void warning(String log){
        String reLog = this.basePlugin.getBaseStringService().chatColorCodes(log);
        this.basePlugin.getLogger().warning(reLog);
    }

    /**
     * 根据语言配置打印info日志
     * @param key 语言配置名称
     * @param defualt 默认语言
     * @param prms 需要替换的字符串
     * @return void
     * @author LW-MrWU
     * @date 2021/1/28 12:02
     */
    public void infoByLanguage(String key,String defualt,String ...prms){
        String log = this.basePlugin.getBaseLanguageService().getLanguage(key, defualt, prms);
        this.basePlugin.getLogger().info(log);
    }

    /**
     * 根据语言配置打印warning日志
     * @param key 语言配置名称
     * @param defualt 默认语言
     * @param prms 需要替换的字符串
     * @return void
     * @author LW-MrWU
     * @date 2021/1/28 12:03
     */
    public void warningByLanguage(String key,String defualt,String ...prms){
        String log = this.basePlugin.getBaseLanguageService().getLanguage(key, defualt, prms);
        this.basePlugin.getLogger().warning(log);
    }
}
