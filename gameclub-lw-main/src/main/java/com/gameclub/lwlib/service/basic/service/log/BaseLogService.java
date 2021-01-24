package com.gameclub.lwlib.service.basic.service.log;

import com.gameclub.lwlib.service.basic.service.plugin.BasePlugin;

/**
 * @author lw
 * @date 创建时间 2021/1/16 18:13
 * @description 日志基础服务实现类
 */
public class BaseLogService {
    protected BasePlugin basePlugin;

    /**
     * 构造函数
     * @author lw
     * @date 2021/1/16
     * @param [basePlugin]
     * @return
     */
    public BaseLogService(BasePlugin basePlugin){
        this.basePlugin = basePlugin;
    }

    /**
     * 打印info日志
     * @author lw
     * @date 2021/1/16
     * @param [log]
     * @return void
     */
    public void info(String log){
        String reLog = this.basePlugin.getBaseStringService().chatColorCodes(log);
        this.basePlugin.getLogger().info(reLog);
    }

    /**
     * 打印warning日志
     * @author lw
     * @date 2021/1/16
     * @param [log]
     * @return void
     */
    public void warning(String log){
        String reLog = this.basePlugin.getBaseStringService().chatColorCodes(log);
        this.basePlugin.getLogger().warning(reLog);
    }

    /**
     * 根据语言配置打印info日志
     * @author lw
     * @date 2021/1/16
     * @param [log]
     * @return void
     */
    public void infoByLanguage(String key,String defualt,String ...prms){
        String log = this.basePlugin.getBaseLanguageService().getLanguage(key, defualt, prms);
        this.basePlugin.getLogger().info(log);
    }

    /**
     * 根据语言配置打印warning日志
     * @author lw
     * @date 2021/1/16
     * @param [log]
     * @return void
     */
    public void warningByLanguage(String key,String defualt,String ...prms){
        String log = this.basePlugin.getBaseLanguageService().getLanguage(key, defualt, prms);
        this.basePlugin.getLogger().warning(log);
    }
}
