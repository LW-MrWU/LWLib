package com.gameclub.service.basic.service.config;

import com.gameclub.service.basic.service.plugin.BasePlugin;

/**
 * @author lw
 * @date 创建时间 2021/1/16 20:03
 * @description 基础配置服务
 */
public class BaseConfigService {
    protected BasePlugin basePlugin;

    /**
     * 构造函数
     * @author lw
     * @date 2021/1/16
     * @param [basePlugin]
     * @return
     */
    public BaseConfigService(BasePlugin basePlugin){
        this.basePlugin = basePlugin;
    }

}
