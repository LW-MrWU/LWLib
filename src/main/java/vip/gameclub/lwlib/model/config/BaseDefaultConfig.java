package vip.gameclub.lwlib.model.config;

import vip.gameclub.lwlib.service.plugin.BasePlugin;
import vip.gameclub.lwlib.model.enumModel.BaseLanguageEnum;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * config配置文件父类
 * @author LW-MrWU
 * @date 创建时间 2021/1/22 15:24
 */
public abstract class BaseDefaultConfig <T extends BasePlugin> extends BaseConfig {

    /**
     * 构造函数
     * @param basePlugin 启动主类
     * @return
     * @author LW-MrWU
     * @date 2021/1/28 11:35
     */
    public BaseDefaultConfig(T basePlugin) {
        super(basePlugin, "config.yml");
    }

    /**
     * 加载配置额外操作
     * @param
     * @return void
     * @author LW-MrWU
     * @date 2021/1/28 11:35
     */
    @Override
    public void loadConfig() {
        FileConfiguration fileConfiguration = this.getFileConfiguration();
        if(fileConfiguration != null){
            String lang = fileConfiguration.getString("lang");
            if(StringUtils.isNotEmpty(lang)){
                BaseLanguageEnum baseLanguageEnum = BaseLanguageEnum.getEnumByName(lang);
                String fileName = baseLanguageEnum.getValue();

                BasePlugin basePlugin = this.getBasePlugin();
                BaseLanguageConfig baseLanguageConfig = new BaseLanguageConfig(basePlugin, fileName);
            }
        }

        loadDefaultConfig();
    }

    /**
     * config基础文件配置自定义拓展操作
     * @param
     * @return void
     * @author LW-MrWU
     * @date 2021/1/28 11:36
     */
    protected abstract void loadDefaultConfig();

    @Override
    protected boolean createConfig() {
        return false;
    }
}
