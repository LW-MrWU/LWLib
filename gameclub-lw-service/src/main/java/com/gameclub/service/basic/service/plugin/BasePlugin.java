package com.gameclub.service.basic.service.plugin;

import com.gameclub.service.basic.service.log.BaseLogService;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author lw
 * @date 创建时间 2021/1/16 18:36
 * @description 插件父类
 */
public abstract class BasePlugin extends JavaPlugin {

    private BaseLogService baseLogService;
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
     * 服务启动
     * @author lw
     * @date 2021/1/16
     * @param []
     * @return void
     */
    @Override
    public void onEnable(){
        initService();
        boolean flag = enable();
        if(!flag){
            BasePlugin tempPlugin = this;
            tempPlugin.setEnabled(false);
            this.baseLogService.warning("lw mc基础脚手架加载失败，关闭插件");
        }
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
