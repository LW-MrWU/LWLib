package vip.gameclub.lwlib.llb;

import vip.gameclub.lwlib.model.config.BaseDefaultConfig;

/**
 * lwlib 配置类
 * @author LW-MrWU
 * @date 创建时间 2021/1/22 16:54
 */
public class LwLibDefaultConfig extends BaseDefaultConfig {
    /**
     * 构造函数
     * @param
     * @return
     * @author LW-MrWU
     * @date 2021/1/28 11:10
     */
    public LwLibDefaultConfig() {
        super(LwLibMainPlugin.getInstance());
    }

    @Override
    protected void loadDefaultConfig() {
    }
}
