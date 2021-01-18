package com.gameclub.service.basic.service.plugin;

/**
 * @author lw
 * @date 创建时间 2021/1/16 18:36
 * @description 服务启动时调用
 */
public class OnEnablePlugin extends BasePlugin {
    @Override
    public boolean enable() {
        getBaseLogService().info("$a成功加载 LWMcScaffold");
        return true;
    }
}
