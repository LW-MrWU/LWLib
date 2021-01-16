package com.gameclub.lw.basic.service.plugin;

/**
 * @author lw
 * @date 创建时间 2021/1/16 18:36
 * @description 服务启动时调用
 */
public class OnEnablePlugin extends BasePlugin {
    @Override
    public boolean enable() {
        getBaseLogService().info("成功加载lw mc基础脚手架");
        return true;
    }
}
