package com.gameclub.lwlib.model.config;

import com.gameclub.lwlib.model.enumModel.BaseLanguageEnum;
import com.gameclub.lwlib.model.enumModel.BaseSysMsgEnum;
import com.gameclub.lwlib.service.basic.service.plugin.BasePlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.file.CopyOption;
import java.nio.file.Files;

/**
 * @author lw
 * @date 创建时间 2021/1/18 14:51
 * @description 所有配置文件的父类
 */
public abstract class BaseConfig<T extends BasePlugin> {
    private T basePlugin;
    private File file;
    private FileConfiguration fileConfiguration;
    private String fileName;
    private String folder;
    private boolean init = true;

    /**
     * 构造函数
     * @author lw
     * @date 2021/1/22 15:08
     * @param [basePlugin 主服务, fileName 文件名]
     * @return
     */
    public BaseConfig(T basePlugin, String fileName){
        this(basePlugin, fileName, null);
    }

    /**
     * 构造函数（文件夹）
     * @author lw
     * @date 2021/1/22 15:08
     * @param [basePlugin 主服务, fileName 文件名, folder 文件夹]
     * @return
     */
    public BaseConfig(T basePlugin, String fileName, String folder){
        this.basePlugin = basePlugin;
        this.fileName = fileName;
        this.folder = folder;

        String pluginRealFilePath = this.basePlugin.getBaseConfigService().getPluginRealFilePath(this);
        this.file = new File(pluginRealFilePath);

        if(!file.exists()){
            this.file.getParentFile().mkdirs();
            String sysRealFilePath = this.basePlugin.getBaseConfigService().getSysRealFilePath(this);
            InputStream inputStream = this.basePlugin.getResource(sysRealFilePath);

            if(inputStream != null){
                try{
                    Files.copy(inputStream, this.file.toPath(), new CopyOption[0]);
                }catch (IOException e){
                    this.basePlugin.getBaseLogService().infoByLanguage(BaseSysMsgEnum.CONFIG_SAVE_EXCEPTION.name(), BaseSysMsgEnum.CONFIG_SAVE_EXCEPTION.getValue(), fileName, e.getMessage());
                }
                this.basePlugin.getBaseLogService().infoByLanguage(BaseSysMsgEnum.CONFIG_NOT_FOUND.name(), BaseSysMsgEnum.CONFIG_NOT_FOUND.getValue(), fileName);
            }else{
                if(!createConfig()){
                    this.init = false;
                    return;
                }
            }
        }

        this.loadFileConfig();
        this.load();
        this.basePlugin.getBaseConfigService().registerConfig(this);
    }

    /**
     * 加载fileconfig
     * @author lw
     * @date 2021/1/22 14:42
     * @param []
     * @return void
     */
    protected void loadFileConfig() {
        if(this.fileConfiguration==null) {
            this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
        }
    }

    /**
     * 重新加载
     * @author lw
     * @date 2021/1/22 15:13
     * @param []
     * @return void
     */
    public void reload() {
        if(this.isInit()) {
            this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
            this.basePlugin.getBaseLogService().infoByLanguage(BaseSysMsgEnum.CONFIG_RELOAD_SUCCESS.name(), BaseSysMsgEnum.CONFIG_RELOAD_SUCCESS.getValue(), this.fileName);
        }
    }

    /**
     * 加载配置
     * @author lw
     * @date 2021/1/22 15:13
     * @param []
     * @return void
     */
    public void load(){
        if(this.isInit()){
            loadConfig();
        }
    }

    /**
     * 加载配置文件后的额外操作
     * @author lw
     * @date 2021/1/22 14:45
     * @param []
     * @return void
     */
    public abstract void loadConfig();

    /**
     * 创建自定义配置（例如玩家文件）
     * @author lw
     * @date 2021/1/22 15:00
     * @param []
     * @return boolean
     */
    protected abstract boolean createConfig();

    /**
     * 创建一个新的配置文件
     * @author lw
     * @date 2021/1/22 15:17
     * @param []
     * @return boolean
     */
    public boolean createNewConfigFile() {
        boolean state = false;
        try {
            this.file.createNewFile();
            this.loadFileConfig();
            state = true;
        } catch (IOException e) {

        }
        return state;
    }

    /**
     * 获取配置文件FileConfiguration
     * @author lw
     * @date 2021/1/22 11:29
     * @param []
     * @return org.bukkit.configuration.file.FileConfiguration
     */
    public FileConfiguration getFileConfiguration() {
        return this.fileConfiguration;
    }

    public File getFile() {
        return this.file;
    }

    public String getFileName(){
        return this.fileName;
    }

    public String getFolder(){
        return this.folder;
    }

    public boolean isInit() {
        return init;
    }

    public T getBasePlugin() {
        return basePlugin;
    }
}
