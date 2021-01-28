package vip.gameclub.lwlib.service.config;

import vip.gameclub.lwlib.model.config.BaseConfig;
import vip.gameclub.lwlib.service.plugin.BasePlugin;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LW-MrWU
 * @date 创建时间 2021/1/16 20:03
 * 基础配置文件服务
 */
public class BaseConfigService <T extends BaseConfig> {
    protected BasePlugin basePlugin;
    private Map<String, T> configs = new HashMap<String, T>();

    /**
     * 构造函数
     * @param basePlugin 启动主类
     * @return
     * @author LW-MrWU
     * @date 2021/1/28 11:47
     */
    public BaseConfigService(BasePlugin basePlugin){
        this.basePlugin = basePlugin;
    }

    /**
     * 获取插件配置文件夹文件绝对路径
     * @param config 配置实体
     * @return java.lang.String
     * @author LW-MrWU
     * @date 2021/1/28 11:47
     */
    public String getPluginRealFilePath(T config){
        String fileName = config.getFileName();
        String folder = config.getFolder();
        String basePath = basePlugin.getDataFolder().toString();
        //系统默认分隔符
        String systemSeparator = System.getProperty("file.separator");

        if(StringUtils.isEmpty(folder)){
            return basePath + systemSeparator + fileName;
        }

        folder = folder.replace("/", systemSeparator);
        return basePath + systemSeparator + folder + systemSeparator + fileName;
    }

    /**
     * 获取插件内置配置文件夹文件绝对路径
     * @param config 配置实体
     * @return java.lang.String
     * @author LW-MrWU
     * @date 2021/1/28 11:48
     */
    public String getSysRealFilePath(T config){
        String fileName = config.getFileName();
        String folder = config.getFolder();
        //系统默认分隔符
        //String systemSeparator = System.getProperty("file.separator");

        if(StringUtils.isEmpty(folder)){
            return fileName;
        }

        return folder + "/" + fileName;
    }

    /**
     * 注册配置文件
     * @param configParams 文件实体
     * @return void
     * @author LW-MrWU
     * @date 2021/1/28 11:49
     */
    public void registerConfig(T... configParams) {
        for (T config : configParams){
            String key = getPluginRealFilePath(config);
            configs.put(key, config);
        }
    }

    /**
     * 获取配置文件实体
     * @param configFileName 配置绝对路径
     * @return T
     * @author LW-MrWU
     * @date 2021/1/28 11:50
     */
    @SuppressWarnings("unchecked")
    public T getConfig(String configFileName){
        T tempresult = null;
        BaseConfig tempconfig = configs.get(configFileName);
        if(tempconfig!=null) {
            tempresult = (T)tempconfig;
        }
        return tempresult;
    }

    /**
     * 重载所有配置文件
     * @param
     * @return void
     * @author LW-MrWU
     * @date 2021/1/28 11:51
     */
    public void reloadAll(){
        for (T config : configs.values()){
            config.reload();
        }
    }
}
