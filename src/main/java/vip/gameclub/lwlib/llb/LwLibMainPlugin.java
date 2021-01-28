package vip.gameclub.lwlib.llb;

import vip.gameclub.lwlib.model.enumModel.BaseSysMsgEnum;
import vip.gameclub.lwlib.service.plugin.BasePlugin;

/**
 * @author LW-MrWU
 * @date 创建时间 2021/1/16 18:36
 * lwlib服务主类
 */
public class LwLibMainPlugin extends BasePlugin {
    private static LwLibMainPlugin lwLibMainPlugin;

    /**
     * 实例化方法
     * @param
     * @return vip.gameclub.lwlib.llb.LwLibMainPlugin
     * @author LW-MrWU
     * @date 2021/1/28 11:12
     */
    public static LwLibMainPlugin getInstance(){
        return lwLibMainPlugin;
    }

    @Override
    public boolean enable() {
        lwLibMainPlugin = this;

        //初始化配置文件
        initConfig();
        //成功加载提示
        getBaseLogService().infoByLanguage(BaseSysMsgEnum.SUCCESS_LOAD.name(), BaseSysMsgEnum.SUCCESS_LOAD.getValue());
        return true;
    }

    @Override
    public boolean disable() {
        return false;
    }

    /**
     * 初始化配置文件
     * @param
     * @return void
     * @author LW-MrWU
     * @date 2021/1/28 11:12
     */
    private void initConfig(){
        //LwLibDefaultConfig defaultConfig = new LwLibDefaultConfig();
    }
}
