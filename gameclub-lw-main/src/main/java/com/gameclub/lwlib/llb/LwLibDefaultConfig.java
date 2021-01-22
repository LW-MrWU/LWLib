package com.gameclub.lwlib.llb;

import com.gameclub.lwlib.model.config.BaseDefaultConfig;

/**
 * @author lw
 * @date 创建时间 2021/1/22 16:54
 * @description lwlib 配置类
 */
public class LwLibDefaultConfig extends BaseDefaultConfig {
    public LwLibDefaultConfig() {
        super(LwLibMainPlugin.getInstance());
    }

    @Override
    protected void loadDefaultConfig() {
    }
}
