package com.gameclub.model.config;

import com.gameclub.service.basic.service.plugin.BasePlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;

/**
 * @author lw
 * @date 创建时间 2021/1/18 14:51
 * @description 所有配置文件的父类
 */
public abstract class BaseConfig {

    private boolean init = false;
    private String fileName;
    private File file;
    private FileConfiguration config;
    private BasePlugin basePlugin;

    /**
     * 构造函数
     * @author lw
     * @date 2021/1/18 14:51
     * @param [fileName 文件名, plugin 插件]
     * @return
     */
    public BaseConfig(String fileName, BasePlugin basePlugin) {
        this.fileName = fileName;
        this.basePlugin = basePlugin;
        this.file = new File(getConfigFilePath());

        try {
            if (!this.file.exists()) {
                this.file.getParentFile().mkdirs();

                InputStream inputStream = this.basePlugin.getResource(this.fileName);

                if(inputStream!=null) {
                    Files.copy(inputStream,
                            this.file.toPath(), new CopyOption[0]);

                    this.loadFileConfig();
                    init = true;
                }else {
                    if(createConfig()) {
                        this.loadFileConfig();
                        init = true;
                    }
                }
            }else {
                this.loadFileConfig();
                init = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(this.isInit()) {
            load();
        }
    }

    /**
     * 配置全路径
     * @author lw
     * @date 2021/1/18 14:52
     * @param []
     * @return java.lang.String
     */
    protected String getConfigFilePath() {
        return this.basePlugin.getDataFolder()+"/"+this.getFileName();
    }

    /**
     * 加载fileconfig
     * @author lw
     * @date 2021/1/18 14:57
     * @param []
     * @return void
     */
    protected void loadFileConfig() {
        if(this.config==null) {
            this.config = YamlConfiguration.loadConfiguration(this.file);
        }
    }

    /**
     * 创建配置
     * @author lw
     * @date 2021/1/18 14:58
     * @param []
     * @return boolean
     */
    protected abstract boolean createConfig();

    /**
     * 加载配置
     * @author lw
     * @date 2021/1/18 15:00
     * @param []
     * @return void
     */
    public void load() {
        if(this.isInit()) {
            loadConfig();
        }
    }

    /**
     * 加载配置文件
     * @author lw
     * @date 2021/1/18 15:00
     * @param []
     * @return void
     */
    public abstract void loadConfig();

    /**
     * 重新加载
     * @author lw
     * @date 2021/1/18 15:04
     * @param []
     * @return void
     */
    public void reload() {
        if(this.isInit()) {
            this.config = YamlConfiguration.loadConfiguration(this.file);
        }
    }

    /**
     * 添加配置
     * @author lw
     * @date 2021/1/18 15:01
     * @param [key, val]
     * @return void
     */
    public void setProperties(String key,Object val) {
        this.getConfig().set(key, val);
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void setConfig(FileConfiguration config) {
        this.config = config;
    }

    public BasePlugin getBasePlugin() {
        return basePlugin;
    }

    public void setBasePlugin(BasePlugin basePlugin) {
        this.basePlugin = basePlugin;
    }
}
