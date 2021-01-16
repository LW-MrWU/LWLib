package com.gameclub.lw.basic.service.log;

import com.gameclub.lw.basic.service.plugin.BasePlugin;

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
        this.basePlugin.getLogger().info(log);
    }

    /**
     * 打印warning日志
     * @author lw
     * @date 2021/1/16
     * @param [log]
     * @return void
     */
    public void warning(String log){
        this.basePlugin.getLogger().warning(log);
    }
}
