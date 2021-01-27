package vip.gameclub.lwlib.service.config;

import vip.gameclub.lwlib.model.config.BaseConfig;
import vip.gameclub.lwlib.service.plugin.BasePlugin;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lw
 * @date 创建时间 2021/1/16 20:03
 * @description 基础配置服务
 */
public class BaseConfigService <T extends BaseConfig> {
    protected BasePlugin basePlugin;
    private Map<String, T> configs = new HashMap<String, T>();

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
     * 获取插件配置文件夹文件绝对路径
     * @author lw
     * @date 2021/1/22 12:02
     * @param [fileName, folder]
     * @return java.lang.String
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
     * 获取系统配置文件绝对路径
     * @author lw
     * @date 2021/1/22 12:02
     * @param [fileName, folder]
     * @return java.lang.String
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
     * @author lw
     * @date 2021/1/22 13:37
     * @param [configParams]
     * @return void
     */
    public void registerConfig(T... configParams) {
        for (T config : configParams){
            String key = getPluginRealFilePath(config);
            configs.put(key, config);
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
     * @author lw
     * @date 2021/1/23
     * @param []
     * @return void
     */
    public void reloadAll(){
        for (T config : configs.values()){
            config.reload();
        }
    }
}
