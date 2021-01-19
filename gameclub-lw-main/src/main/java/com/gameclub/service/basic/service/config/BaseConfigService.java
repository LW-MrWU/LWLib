package com.gameclub.service.basic.service.config;

import com.gameclub.model.config.BaseConfig;
import com.gameclub.service.basic.service.plugin.BasePlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author lw
 * @date 创建时间 2021/1/16 20:03
 * @description 基础配置服务
 */
public class BaseConfigService {
    protected BasePlugin basePlugin;
    private Map<String,BaseConfig> configs = new HashMap<String,BaseConfig>();

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

    /**
     * 将配置注册进来
     * @author lw
     * @date 2021/1/18 15:02
     * @param [config]
     * @return void
     */
    public <T extends BaseConfig> void registerConfig(T config){
        if(config.isInit()) {
            String tempkey = config.getFileName();
            configs.put(tempkey, config);
        }else {
            basePlugin.getBaseLogService().warning(config.getFileName() + " 由于初始化失败，配置文件无法注册！");
        }
    }

    /**
     * 重新加载某个config
     * @author lw
     * @date 2021/1/18 15:03
     * @param [configFileName]
     * @return void
     */
    public void reloadConfig(String configFileName) {
        BaseConfig tempconfig = configs.get(configFileName);
        tempconfig.reload();
    }

    /**
     * 重载所有配置文件
     * @author lw
     * @date 2021/1/18 15:06
     * @param []
     * @return void
     */
    public void reloadAllConfig() {
        Set<String> tempkeys = this.configs.keySet();
        for(String tempkey : tempkeys) {
            reloadConfig(tempkey);
        }
    }

    /**
     * 提供注册的配置
     * @author lw
     * @date 2021/1/18 15:06
     * @param [configFileName]
     * @return T
     */
    @SuppressWarnings("unchecked")
    public <T extends BaseConfig> T getConfig(String configFileName){
        T tempresult = null;
        BaseConfig tempconfig = configs.get(configFileName);
        if(tempconfig!=null) {
            tempresult = (T)tempconfig;
        }
        return tempresult;
    }

    /**
     * 根据key查找指定配置文件信息
     * @author lw
     * @date 2021/1/19 14:36
     * @param [fileName 文件名, key 键, defualt 默认值, prms 替换信息]
     * @return java.lang.String
     */
    public String getMsgByConfig(String fileName, String key,String defualt,String ...prms) {
        BaseConfig config = getConfig(fileName);

        String msg = null;
        if(config!=null) {
            msg = config.getConfig().getString(key, defualt);
            if(msg==null) {
                msg = defualt;
            }
        }else {
            msg = defualt;
        }
        msg = this.basePlugin.getBaseUtilsService().substitutionPrms(msg, prms);
        return msg;
    }
}
