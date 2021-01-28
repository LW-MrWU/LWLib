package vip.gameclub.lwlib.model.config;

import vip.gameclub.lwlib.model.enumModel.BaseSysMsgEnum;
import vip.gameclub.lwlib.service.plugin.BasePlugin;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.file.CopyOption;
import java.nio.file.Files;

/**
 * @author LW-MrWU
 * @date 创建时间 2021/1/18 14:51
 * 所有配置文件的父类
 */
public abstract class BaseConfig <T extends BasePlugin> {
    private T basePlugin;
    private File file;
    private FileConfiguration fileConfiguration;
    private String fileName;
    private String folder;
    private boolean init = true;

    /**
     * 配置绝对路径
     */
    public static String configName;

    /**
     * 构造函数
     * @param basePlugin 主服务
     * @param fileName 文件名
     * @return
     * @author LW-MrWU
     * @date 2021/1/28 11:25
     */
    public BaseConfig(T basePlugin, String fileName){
        this(basePlugin, fileName, null);
    }

    /**
     * 构造函数
     * 带文件夹，可多级，用/分隔
     * @param basePlugin 主服务
     * @param fileName 文件名
     * @param folder 文件夹名
     * @return
     * @author LW-MrWU
     * @date 2021/1/28 11:26
     */
    public BaseConfig(T basePlugin, String fileName, String folder){
        this.basePlugin = basePlugin;
        this.fileName = fileName;
        this.folder = folder;

        String pluginRealFilePath = this.basePlugin.getBaseConfigService().getPluginRealFilePath(this);
        this.file = new File(pluginRealFilePath);

        String sysRealFilePath = this.basePlugin.getBaseConfigService().getSysRealFilePath(this);

        if(!file.exists()){
            if(!fileNotExist(sysRealFilePath)){
                return;
            }
        }else{
            configVersionHandler(sysRealFilePath);
        }
        this.loadFileConfig();
        this.load();
        this.basePlugin.getBaseConfigService().registerConfig(this);
    }

    /**
     * 配置文件找不到时的操作
     * @param sysRealFilePath 插件内置配置文件夹文件绝对路径
     * @return boolean
     * @author LW-MrWU
     * @date 2021/1/28 11:27
     */
    private boolean fileNotExist(String sysRealFilePath){
        InputStream inputStream = this.basePlugin.getResource(sysRealFilePath);
        this.file.getParentFile().mkdirs();

        if(inputStream != null){
            try{
                Files.copy(inputStream, this.file.toPath(), new CopyOption[0]);
                inputStream.close();
            }catch (IOException e){
                this.basePlugin.getBaseLogService().infoByLanguage(BaseSysMsgEnum.CONFIG_SAVE_EXCEPTION.name(), BaseSysMsgEnum.CONFIG_SAVE_EXCEPTION.getValue(), fileName, e.getMessage());
            }
            this.basePlugin.getBaseLogService().infoByLanguage(BaseSysMsgEnum.CONFIG_NOT_FOUND.name(), BaseSysMsgEnum.CONFIG_NOT_FOUND.getValue(), fileName);
        }else{
            if(!createConfig()){
                this.init = false;
                return false;
            }
        }
        return true;
    }

    /**
     * 处理配置version
     * version不对应时，将系统文件覆盖到服务器插件文件
     * @param sysRealFilePath 插件内置配置文件夹文件绝对路径
     * @return void
     * @author LW-MrWU
     * @date 2021/1/28 11:28
     */
    private void configVersionHandler(String sysRealFilePath){
        InputStream inputStream = this.basePlugin.getResource(sysRealFilePath);
        //判断config版本
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String sysVersion = "";
        try{
            String line;
            while((line = bufferedReader.readLine()) != null) {
                //过滤掉注释行数据
                if(!line.startsWith("#")) {
                    String [] strings = line.split(":");
                    if(strings.length > 1) {
                        String key = strings[0];
                        if("version".equalsIgnoreCase(key)){
                            sysVersion = strings[1].trim();
                            break;
                        }
                    }
                }
            }
            bufferedReader.close();
            inputStream.close();
        }catch (IOException e){
            this.basePlugin.getBaseLogService().infoByLanguage(BaseSysMsgEnum.CONFIG_SAVE_EXCEPTION.name(), BaseSysMsgEnum.CONFIG_SAVE_EXCEPTION.getValue(), fileName, e.getMessage());
        }
        if(StringUtils.isNotEmpty(sysVersion)){
            //判断系统文件version与服务器plugin文件夹中config配置内version是否一致，不一致则更新覆盖plugin文件夹中config内容
            this.loadFileConfig();
            String version = fileConfiguration.getString("version");
            if(StringUtils.isNotEmpty(version) && !sysVersion.equalsIgnoreCase(version)){
                try{
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    InputStream is = this.basePlugin.getResource(sysRealFilePath);
                    byte[] bytes = new byte[1024];
                    int len = -1;
                    while((len = is.read(bytes)) != -1){
                        fileOutputStream.write(bytes,0,len);
                    }
                    is.close();
                    fileOutputStream.close();
                }catch (IOException e){
                    this.basePlugin.getBaseLogService().infoByLanguage(BaseSysMsgEnum.CONFIG_SAVE_EXCEPTION.name(), BaseSysMsgEnum.CONFIG_SAVE_EXCEPTION.getValue(), fileName, e.getMessage());
                }
            }
        }
    }

    /**
     * 加载配置
     * @param
     * @return void
     * @author LW-MrWU
     * @date 2021/1/28 11:29
     */
    protected void loadFileConfig() {
        if(this.fileConfiguration==null) {
            this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
        }
    }

    /**
     * 重新加载配置
     * @param
     * @return void
     * @author LW-MrWU
     * @date 2021/1/28 11:29
     */
    public void reload() {
        if(this.isInit()) {
            this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
            this.basePlugin.getBaseLogService().infoByLanguage(BaseSysMsgEnum.CONFIG_RELOAD_SUCCESS.name(), BaseSysMsgEnum.CONFIG_RELOAD_SUCCESS.getValue(), this.fileName);
        }
    }

    /**
     * 加载配置后额外操作
     * @param
     * @return void
     * @author LW-MrWU
     * @date 2021/1/28 11:30
     */
    public void load(){
        if(this.isInit()){
            loadConfig();
        }
    }

    /**
     * 加载配置文件后的自定义额外操作
     * @param  1
     * @return void
     * @author LW-MrWU
     * @date 2021/1/28 11:30
     */
    public abstract void loadConfig();

    /**
     * 创建自定义配置（例如玩家文件）
     * @param
     * @return boolean
     * @author LW-MrWU
     * @date 2021/1/28 11:30
     */
    protected abstract boolean createConfig();

    /**
     * 创建一个新的配置文件
     * @param
     * @return boolean
     * @author LW-MrWU
     * @date 2021/1/28 11:30
     */
    public boolean createNewConfigFile() {
        boolean state = false;
        try {
            this.file.createNewFile();
            this.loadFileConfig();
            state = true;
        } catch (IOException e) {
            this.basePlugin.getBaseLogService().warningByLanguage(BaseSysMsgEnum.CONFIG_SAVE_EXCEPTION.name(), BaseSysMsgEnum.CONFIG_SAVE_EXCEPTION.getValue(), fileName, e.getMessage());
        }
        return state;
    }

    /**
     * 保存配置
     * 实际保存到文件中
     * @param
     * @return void
     * @author LW-MrWU
     * @date 2021/1/28 11:31
     */
    public void saveConfig() {
        try {
            if(this.isInit()) {
                this.fileConfiguration.save(getFile());
            }
        } catch (IOException e) {
            this.basePlugin.getBaseLogService().warningByLanguage(BaseSysMsgEnum.CONFIG_SAVE_EXCEPTION.name(), BaseSysMsgEnum.CONFIG_SAVE_EXCEPTION.getValue(), fileName, e.getMessage());
        }
    }

    /**
     * 添加/修改/删除配置
     * value输入null时为删除
     * 修改内存中数据，只做临时读取用，重新加载后失效
     * 永久保存请在修改后使用saveConfig方法
     * @param path 路径
     * @param value 值
     * @return void
     * @author LW-MrWU
     * @date 2021/1/28 11:31
     */
    public void setProperties(String path,Object value) {
        this.fileConfiguration.set(path, value);
    }

    /**
     * 获取配置文件FileConfiguration
     * @param
     * @return org.bukkit.configuration.file.FileConfiguration
     * @author LW-MrWU
     * @date 2021/1/28 11:32
     */
    public FileConfiguration getFileConfiguration() {
        return this.fileConfiguration;
    }

    /**
     * 获取文件
     * @param
     * @return java.io.File
     * @author LW-MrWU
     * @date 2021/1/28 11:32
     */
    public File getFile() {
        return this.file;
    }

    /**
     * 获取文件名
     * @param
     * @return java.lang.String
     * @author LW-MrWU
     * @date 2021/1/28 11:33
     */
    public String getFileName(){
        return this.fileName;
    }

    /**
     * 获取文件夹
     * @param
     * @return java.lang.String
     * @author LW-MrWU
     * @date 2021/1/28 11:33
     */
    public String getFolder(){
        return this.folder;
    }

    /**
     * 是否初始化
     * @param
     * @return boolean
     * @author LW-MrWU
     * @date 2021/1/28 11:33
     */
    public boolean isInit() {
        return init;
    }

    /**
     * 获取启动主类
     * @param
     * @return T
     * @author LW-MrWU
     * @date 2021/1/28 11:33
     */
    public T getBasePlugin() {
        return basePlugin;
    }

    /**
     * 获取配置绝对路径
     * @param
     * @return java.lang.String
     * @author LW-MrWU
     * @date 2021/1/28 11:33
     */
    public static <T extends BaseConfig> String getConfigName() {
        return T.configName;
    }

    /**
     * 设置配置绝对路径
     * @param basePlugin 启动主类
     * @param config 配置实体
     * @return void
     * @author LW-MrWU
     * @date 2021/1/28 11:34
     */
    public static <T extends BaseConfig> void setConfigName(BasePlugin basePlugin, T config) {
        String pluginRealFilePath = basePlugin.getBaseConfigService().getPluginRealFilePath(config);
        T.configName = pluginRealFilePath;
    }
}
